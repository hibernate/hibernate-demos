package it.redhat.demo.task;

import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.query.remote.ProtobufMetadataManager;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;

import it.redhat.demo.model.Board;
import it.redhat.demo.model.BoardId;
import it.redhat.demo.model.BoardMessage;
import it.redhat.demo.model.BoardMessageId;
import it.redhat.demo.model.Message;
import it.redhat.demo.model.MessageId;
import it.redhat.demo.model.MessageTag;
import it.redhat.demo.model.MessageTagId;
import it.redhat.demo.model.Tag;
import it.redhat.demo.model.TagId;

public class RegisterMarshallersTask implements ServerTask {

	private static final int ANY_NOTNULL_NUMBER = 7;
	private TaskContext ctx;

	@Override
	public void setTaskContext(TaskContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public String getName() {
		return RegisterMarshallersTask.class.getSimpleName();
	}

	@Override
	public Object call() throws Exception {
		EmbeddedCacheManager cacheManager = ctx.getCache().get().getCacheManager();
		ProtobufMetadataManager protobufMetadataManager = cacheManager.getGlobalComponentRegistry().getComponent( ProtobufMetadataManager.class );

		protobufMetadataManager.registerMarshaller( new Board.Marshaller() );
		protobufMetadataManager.registerMarshaller( new BoardId.Marshaller() );
		protobufMetadataManager.registerMarshaller( new BoardMessage.Marshaller() );
		protobufMetadataManager.registerMarshaller( new BoardMessageId.Marshaller() );
		protobufMetadataManager.registerMarshaller( new Message.Marshaller() );
		protobufMetadataManager.registerMarshaller( new MessageId.Marshaller() );
		protobufMetadataManager.registerMarshaller( new MessageTag.Marshaller() );
		protobufMetadataManager.registerMarshaller( new MessageTagId.Marshaller() );
		protobufMetadataManager.registerMarshaller( new Tag.Marshaller() );
		protobufMetadataManager.registerMarshaller( new TagId.Marshaller() );

		return ANY_NOTNULL_NUMBER;
	}

	@Override
	public TaskExecutionMode getExecutionMode() {
		// Registering protofile should be done in all nodes
		return TaskExecutionMode.ALL_NODES;
	}
}
