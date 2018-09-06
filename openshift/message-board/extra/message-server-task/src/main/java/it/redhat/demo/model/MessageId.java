package it.redhat.demo.model;

import java.io.IOException;
import java.util.Objects;

import org.infinispan.protostream.MessageMarshaller;

public class MessageId {

	private Long id;

	public MessageId() {
	}

	public MessageId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		MessageId messageId = (MessageId) o;
		return Objects.equals( id, messageId.id );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "MessageId{" );
		sb.append( "id=" ).append( id );
		sb.append( '}' );
		return sb.toString();
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Message_id {
	 *   required int64 id = 1;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<MessageId> {

		@Override
		public MessageId readFrom(ProtoStreamReader reader) throws IOException {
			MessageId messageId = new MessageId();
			messageId.id = reader.readLong( "id" );

			return messageId;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, MessageId messageId) throws IOException {
			writer.writeLong( "id", messageId.id );
		}

		@Override
		public Class<? extends MessageId> getJavaClass() {
			return MessageId.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Message_id";
		}
	}
}
