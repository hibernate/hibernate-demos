package org.hibernate.demo.message.post.core.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class BoardTest {

	private static final String USERNAME = "cacciapuoti";

	@Test
	public void testPopMiddleMessage() {
		Message message1 = new Message( USERNAME, "Hi #a!" );
		message1.setId( 1l );

		Message message2 = new Message( USERNAME, "Hi #b!" );
		message2.setId( 2l );

		Message message3 = new Message( USERNAME, "Hi #c!" );
		message3.setId( 3l );

		Message message4 = new Message( USERNAME, "Hi #d!" );
		message4.setId( 4l );

		// add all messages
		Board board = new Board( message1 );
		board.pushMessage( message2 );
		board.pushMessage( message3 );
		board.pushMessage( message4 );

		// pop from the middle
		board.popMessage( message3 );

		// getMessages presents the message in the reverse order
		// for that message4 precedes the other messages
		assertThat( board.getMessages() ).containsExactly( message4, message2, message1 );
	}
}
