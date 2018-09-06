package it.redhat.demo.model;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

public class Board {

	public static final String CACHE_NAME = Board.class.getSimpleName();

	private String username;

	public Board() {
	}

	public Board(String username) {
		this.username = username;
	}

	/*
	 * package HibernateOGMGenerated;
	 *
	 * message Board {
	 *   required string username = 1;
	 * }
	 *
	 */
	public static final class Marshaller implements MessageMarshaller<Board> {

		@Override
		public Board readFrom(ProtoStreamReader reader) throws IOException {
			Board board = new Board();
			board.username = reader.readString( "username" );

			return board;
		}

		@Override
		public void writeTo(ProtoStreamWriter writer, Board board) throws IOException {
			writer.writeString( "username", board.username );
		}

		@Override
		public Class<? extends Board> getJavaClass() {
			return Board.class;
		}

		@Override
		public String getTypeName() {
			return "HibernateOGMGenerated.Board";
		}
	}
}
