package org.hibernate.ogm.hiking.rest.model;

import org.hibernate.ogm.hiking.model.Person;

public class ExternalPerson {

	private long id;
	private String name;

	public ExternalPerson() {
	}

	public ExternalPerson(Person person) {
		this.id = person.id;
		this.name = person.name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PersonDescription [id=" + id + ", name=" + name + "]";
	}

}
