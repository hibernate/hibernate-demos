package org.hibernate.demo.message.post.core.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Board {

	private static final Integer BOARD_MESSAGE_SIZE = 200;

	@Id
	private String username;

	@OneToMany(fetch = FetchType.EAGER)
	@OrderColumn(name = "order")
	private List<Message> messages = new LinkedList<>();

	private Integer next = 0;

	public Board() {
	}

	public Board( Message firstMessage ) {
		this.username = firstMessage.getUsername();
		this.messages.add( firstMessage );
	}

	public List<Message> getMessages() {
		return messages.stream().sorted( Collections.reverseOrder() ).collect( Collectors.toList() );
	}

	public void pushMessage( Message message ) {
		if (messages.size() == BOARD_MESSAGE_SIZE ) {
			((LinkedList)messages).removeFirst();
		}
		messages.add( message );
		next = messages.size();
	}

	public void popMessage(Message message) {
		messages.remove( message );
		next = messages.size();
	}

	public boolean isEmpty() {
		return this.messages.isEmpty();
	}

	public Integer getNext() {
		return next;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Board board = (Board) o;
		return Objects.equals( username, board.username );
	}

	@Override
	public int hashCode() {
		return Objects.hash( username );
	}

	@Override
	public String toString() {
		return "Board{" +
			"username='" + username + '\'' +
			", messages=" + messages +
			'}';
	}
}
