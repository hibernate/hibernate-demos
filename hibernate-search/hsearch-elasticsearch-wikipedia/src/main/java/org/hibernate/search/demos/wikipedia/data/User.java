package org.hibernate.search.demos.wikipedia.data;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

@Entity
@Table(name = "user_") // "user" is an SQL keyword
@Indexed(index = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId")
	@SequenceGenerator(name = "userId", sequenceName = "user_id_seq", allocationSize = 1)
	private Long id;

	@Basic(optional = false)
	@KeywordField(normalizer = "cleaned_keyword")
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
