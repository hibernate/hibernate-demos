package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class MessageTagId {

	public static final String CACHE_NAME = "Message_Tag";

	private Long messageId;
	private String tagsName;

	public MessageTagId() {
	}

	public MessageTagId(Long messageId, String tagsName) {
		this.messageId = messageId;
		this.tagsName = tagsName;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Message_Tag_id {
	 * 	required int64 Message_id = 1;
	 * 	required string tags_name = 2;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<MessageTagId> {

		@Override
		public MessageTagId readFrom(ProtoStreamReader reader) throws IOException {
			MessageTagId messageTagId = new MessageTagId();
			messageTagId.messageId = reader.readLong( "Message_id" );
			messageTagId.tagsName = reader.readString( "tags_name" );

			return messageTagId;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, MessageTagId messageTagId) throws IOException {
			writer.writeLong( "Message_id", messageTagId.messageId );
			writer.writeString( "tags_name", messageTagId.tagsName );
		}

		@Override
		public Class<? extends MessageTagId> getJavaClass() {
			return MessageTagId.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Message_Tag_id";
		}
	}
}
