package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class BoardMessageId {

	private String boardUsername;
	private Integer order;

	public BoardMessageId() {
	}

	public BoardMessageId(String boardUsername, Integer order) {
		this.boardUsername = boardUsername;
		this.order = order;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Board_Message_id {
     *   required string Board_username = 1;
     *   required int32 order = 2;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<BoardMessageId> {

		@Override
		public BoardMessageId readFrom(ProtoStreamReader reader) throws IOException {
			BoardMessageId boardMessageId = new BoardMessageId();
			boardMessageId.boardUsername = reader.readString( "Board_username" );
			boardMessageId.order = reader.readInt( "order" );

			return boardMessageId;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, BoardMessageId boardMessage) throws IOException {
			writer.writeString( "Board_username", boardMessage.boardUsername );
			writer.writeInt( "order", boardMessage.order );
		}

		@Override
		public Class<? extends BoardMessageId> getJavaClass() {
			return BoardMessageId.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Board_Message_id";
		}
	}
}
