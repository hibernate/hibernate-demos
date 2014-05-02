package org.hibernate.ogm.hiking.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;

	@Size(min=3)
	public String name;

	@OneToMany
	public Set<Hike> organizedHikes = new HashSet<>();

	Person() {
	}

	public Person(String name) {
		this.name = name;
	}

}
