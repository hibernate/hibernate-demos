/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.repo;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.model.Person;

/**
 * {@link Person} CRUD operations.
 *
 * @author Gunnar Morling
 */
@ApplicationScoped
public class PersonRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Person create(Person person) {
		entityManager.persist( person );
		return person;
	}

	public Person get(String id) {
		return entityManager.find( Person.class, id );
	}

	public List<Person> getAll() {
		return entityManager.createQuery( "FROM Person p", Person.class ).getResultList();
	}

	public Person save(Person person) {
		return entityManager.merge( person );
	}

	public void remove(Person person) {
		entityManager.remove( person );
		for ( Hike hike : person.getOrganizedHikes() ) {
			hike.setOrganizer( null );
		}
	}
}
