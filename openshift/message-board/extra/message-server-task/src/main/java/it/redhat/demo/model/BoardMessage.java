package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class BoardMessage {

	public static final String CACHE_NAME = "Board_Message";

	private String boardUsername;
	private Long messagesId;
	private Integer order;

	public BoardMessage() {
	}

	public BoardMessage(String boardUsername, Long messagesId, Integer order) {
		this.boardUsername = boardUsername;
		this.messagesId = messagesId;
		this.order = order;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Board_Message {
     *   required string Board_username = 1;
     *   optional int64 messages_id = 2;
     *   required int32 order = 3;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<BoardMessage> {

		@Override
		public BoardMessage readFrom(ProtoStreamReader reader) throws IOException {
			BoardMessage boardMessage = new BoardMessage();
			boardMessage.boardUsername = reader.readString( "Board_username" );
			boardMessage.messagesId = reader.readLong( "messages_id" );
			boardMessage.order = reader.readInt( "order" );

			return boardMessage;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, BoardMessage boardMessage) throws IOException {
			writer.writeString( "Board_username", boardMessage.boardUsername );
			writer.writeLong( "messages_id", boardMessage.messagesId );
			writer.writeInt( "order", boardMessage.order );
		}

		@Override
		public Class<? extends BoardMessage> getJavaClass() {
			return BoardMessage.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Board_Message";
		}
	}
}
