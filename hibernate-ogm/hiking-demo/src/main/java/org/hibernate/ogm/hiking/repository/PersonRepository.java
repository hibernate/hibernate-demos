package org.hibernate.ogm.hiking.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.hiking.model.Person;

@ApplicationScoped
public class PersonRepository {

	@PersistenceContext(unitName="hike-PU-JTA")
	private EntityManager entityManager;

	public List<Person> getAllPersons() {
		return entityManager.createQuery( "from Person", Person.class ).getResultList();
	}

	public Person getPersonById(long personId) {
		return entityManager.find( Person.class, personId );
	}

	public Person createPerson(Person person) {
		entityManager.persist( person );
		return person;
	}
}
