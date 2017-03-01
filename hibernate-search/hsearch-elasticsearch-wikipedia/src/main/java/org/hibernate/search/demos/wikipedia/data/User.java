package org.hibernate.search.demos.wikipedia.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_") // "user" is an SQL keyword
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId")
	@SequenceGenerator(name = "userId", sequenceName = "user_id_seq")
	private Long id;

	@Basic(optional = false)
	private String username;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
