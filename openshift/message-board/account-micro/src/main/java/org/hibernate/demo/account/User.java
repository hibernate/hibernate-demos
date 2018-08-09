package org.hibernate.demo.account;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NaturalId
	private String userName;

	private User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		User user = (User) o;
		return Objects.equals( id, user.id ) && Objects.equals( userName, user.userName );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, userName );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "User{" );
		sb.append( "id=" ).append( id );
		sb.append( ", userName='" ).append( userName ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
