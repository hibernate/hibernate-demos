package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class MessageTag {

	public static final String CACHE_NAME = "Message_Tag";

	private Long messageId;
	private String tagsName;

	public MessageTag() {
	}

	public MessageTag(Long messageId, String tagsName) {
		this.messageId = messageId;
		this.tagsName = tagsName;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Message_Tag {
	 * 	required int64 Message_id = 1;
	 * 	required string tags_name = 2;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<MessageTag> {

		@Override
		public MessageTag readFrom(ProtoStreamReader reader) throws IOException {
			MessageTag messageTag = new MessageTag();
			messageTag.messageId = reader.readLong( "Message_id" );
			messageTag.tagsName = reader.readString( "tags_name" );

			return messageTag;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, MessageTag messageTag) throws IOException {
			writer.writeLong( "Message_id", messageTag.messageId );
			writer.writeString( "tags_name", messageTag.tagsName );
		}

		@Override
		public Class<? extends MessageTag> getJavaClass() {
			return MessageTag.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Message_Tag";
		}
	}
}
