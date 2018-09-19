package it.redhat.demo.model;

import java.io.IOException;
import java.util.Objects;

import org.infinispan.protostream.MessageMarshaller;

public class BoardId {

	private String username;

	public BoardId() {
	}

	public BoardId(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		BoardId boardId = (BoardId) o;
		return Objects.equals( username, boardId.username );
	}

	@Override
	public int hashCode() {
		return Objects.hash( username );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "BoardId{" );
		sb.append( "username='" ).append( username ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Board_id {
     *   required string username = 1;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<BoardId> {

		@Override
		public BoardId readFrom(ProtoStreamReader reader) throws IOException {
			BoardId boardId = new BoardId();
			boardId.username = reader.readString( "username" );

			return boardId;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, BoardId board) throws IOException {
			writer.writeString( "username", board.username );
		}

		@Override
		public Class<? extends BoardId> getJavaClass() {
			return BoardId.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Board_id";
		}
	}
}
