package it.redhat.demo.task;

import java.util.Optional;

import org.infinispan.Cache;
import org.infinispan.commons.dataconversion.IdentityEncoder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;

import it.redhat.demo.model.Board;
import it.redhat.demo.model.BoardId;
import it.redhat.demo.model.BoardMessage;
import it.redhat.demo.model.BoardMessageId;
import it.redhat.demo.model.Message;
import it.redhat.demo.model.MessageId;

public class UpdateUserBoardTask implements ServerTask<Void> {

	private static final String MESSAGE_ID_PARAM_NAME = "messageId";

	private TaskContext taskContext;

	@Override
	public void setTaskContext(TaskContext taskContext) {
		this.taskContext = taskContext;
	}

	@Override
	public Void call() throws Exception {
		Long messageId = (Long) this.taskContext.getParameters().get().get( MESSAGE_ID_PARAM_NAME );

		Cache<MessageId, Message> messages = getCache( Message.CACHE_NAME );
		Cache<BoardId, Board> boards = getCache( Board.CACHE_NAME );
		Cache<BoardMessageId, BoardMessage> joinCache = getCache( BoardMessage.CACHE_NAME );

		// maybe we shall synchronize this get with the previous put
		// let's try without any kind of synchronization
		Message message = messages.get( new MessageId( messageId ) );
		BoardId boardId = new BoardId( message.getUsername() );

		// inserts on the **same user** board must be serialized
		// according to the Infinispan API the lock will be released at the end of transaction
		boards.getAdvancedCache().lock( boardId );

		Board board = boards.get( boardId );
		int boardMessageSize = 0;

		if ( board == null ) {
			// create a board if it doesn't exist
			boards.put( boardId, new Board( message.getUsername() ) );
		} else {
			QueryFactory queryFactory = Search.getQueryFactory( joinCache );
			Query query = queryFactory.from( BoardMessage.class ).having( "boardUsername" ).eq( message.getUsername() ).build();
			boardMessageSize = query.list().size();
		}

		// add message to the board
		BoardMessageId id = new BoardMessageId( message.getUsername(), boardMessageSize );
		BoardMessage value = new BoardMessage( message.getUsername(), messageId, boardMessageSize );
		joinCache.put( id, value );

		return null;
	}

	@Override
	public String getName() {
		return UpdateUserBoardTask.class.getSimpleName();
	}

	private <K, V> Cache<K, V> getCache(String cacheName) {
		// identity encoding is required in order to use the compatibility mode
		return (Cache<K, V>) getCacheManager().getCache( cacheName ).getAdvancedCache().withEncoding( IdentityEncoder.class );
	}

	private EmbeddedCacheManager getCacheManager() {
		Optional<Cache<?, ?>> defaultCache = taskContext.getCache();
		return defaultCache.get().getCacheManager();
	}
}
