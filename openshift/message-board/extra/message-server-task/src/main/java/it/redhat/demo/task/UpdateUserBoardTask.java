package it.redhat.demo.task;

import static it.redhat.demo.task.TranscodingHelper.getWithTranscoding;
import static it.redhat.demo.task.TranscodingHelper.putWithTranscoding;

import java.util.Optional;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;

import it.redhat.demo.model.Board;
import it.redhat.demo.model.BoardId;
import it.redhat.demo.model.BoardMessage;
import it.redhat.demo.model.BoardMessageId;
import it.redhat.demo.model.Message;
import it.redhat.demo.model.MessageId;

public class UpdateUserBoardTask implements ServerTask<Integer> {

	private TaskContext taskContext;
	private Long messageId;

	@Override
	public void setTaskContext(TaskContext taskContext) {
		this.taskContext = taskContext;
		this.messageId = taskContext.getParameters()
				.map( p -> p.get( "messageId" ) )
				.map( Long.class::cast )
				.orElseThrow( () -> new RuntimeException( "Missing parameter 'messageId'" ) );
	}

	/**
	 * Updates the board with the last message.
	 *
	 * @return the new size of the board, after we've added the message
	 * @throws Exception
	 */
	@Override
	public Integer call() throws Exception {
		Cache<MessageId, Message> messages = getCache( Message.CACHE_NAME );
		Cache<BoardId, Board> boards = getCache( Board.CACHE_NAME );
		Cache<BoardMessageId, BoardMessage> joinCache = getCache( BoardMessage.CACHE_NAME );

		MessageId key = new MessageId( messageId );
		Message message = getWithTranscoding( key, messages );
		if ( message == null ) {
			throw new KeyNotFoundException( key, messages );
		}

		TransactionManager transactionManager = boards.getAdvancedCache().getTransactionManager();
		transactionManager.begin();
		BoardId boardId = new BoardId( message.getUsername() );

		// transaction-config LockingMode seems strangely OPTIMISTIC
		// OGM should have been created it PESSIMISTIC
		//TODO: after the problem is solved, we will restore the lock here:
		// // inserts on the **same user** board must be serialized
		// // according to the Infinispan API the lock will be released at the end of transaction
		// --> boards.getAdvancedCache().lock( boardId ); <--

		Board board = getWithTranscoding( boardId, boards );

		if ( board == null ) {
			board = new Board( message.getUsername() );
		}

		// add message to the board
		BoardMessageId id = new BoardMessageId( message.getUsername(), board.getNext() );
		BoardMessage value = new BoardMessage( message.getUsername(), messageId, board.getNext() );
		putWithTranscoding( id, value, joinCache );

		// increment for the next insert
		board.increment();
		putWithTranscoding( boardId, board, boards );

		// free lock here:
		transactionManager.commit();
		return board.getNext();
	}

	@Override
	public String getName() {
		return UpdateUserBoardTask.class.getSimpleName();
	}

	private <K, V> Cache<K, V> getCache(String cacheName) {
		return (Cache<K, V>) getCacheManager().getCache( cacheName );
	}

	private EmbeddedCacheManager getCacheManager() {
		Optional<Cache<?, ?>> defaultCache = taskContext.getCache();
		return defaultCache.get().getCacheManager();
	}
}
