package org.hibernate.ogm.hiking;

import static org.fest.assertions.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.model.Section;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HikeTest {

	private EntityManager entityManager;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setupEntityManager() {
		EntityManagerFactory emf =
				Persistence.createEntityManagerFactory( "hike-PU" );
		entityManager = emf.createEntityManager();
	}

	@Test
	public void simpleEntityTest() {
		entityManager.getTransaction().begin();

		Hike hike = new Hike( "Land's End", "Bristol" );
		entityManager.persist( hike );

		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		hike = entityManager.find( Hike.class, hike.id );

		assertThat( hike ).isNotNull();
		assertThat( hike.destination ).isEqualTo( "Bristol" );

		entityManager.getTransaction().commit();
	}

	@Test
	public void embeddedCollectionTest() {
		entityManager.getTransaction().begin();

		Hike hike = new Hike( "Land's End", "Bristol" );
		hike.sections.add( new Section( "Land's End", "Pendeen" ) );
		hike.sections.add( new Section( "Pendeen", "Perranporth" ) );
		entityManager.persist( hike );

		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		hike = entityManager.find( Hike.class, hike.id );

		assertThat( hike.sections ).hasSize( 2 );
		assertThat( hike.sections.get( 0 ).from ).isEqualTo( "Land's End" );
		assertThat( hike.sections.get( 1 ).from ).isEqualTo( "Pendeen" );

		entityManager.getTransaction().commit();
	}

	@Test
	public void associationTest() {
		entityManager.getTransaction().begin();

		Hike hike = new Hike( "San Francisco", "Oakland" );
		Person bob = new Person( "Bob" );

		hike.organizer = bob;
		bob.organizedHikes.add( hike );

		entityManager.persist( hike );
		entityManager.persist( bob );

		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		hike = entityManager.find( Hike.class, hike.id );

		assertThat( hike.organizer ).isNotNull();
		assertThat( hike.organizer.name ).isEqualTo( "Bob" );

		entityManager.getTransaction().commit();
	}

	@Test
	public void validationTest() {
		exception.expect( Exception.class );

		entityManager.getTransaction().begin();

		Hike hike = new Hike( null, null );

		entityManager.persist( hike );

		entityManager.getTransaction().commit();

	}
}
