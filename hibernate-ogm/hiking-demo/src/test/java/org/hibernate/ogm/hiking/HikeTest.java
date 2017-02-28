package org.hibernate.ogm.hiking;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.model.Section;
import org.hibernate.ogm.hiking.model.Trip;

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

		entityManager.flush();
		entityManager.clear();

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
		Trip trip = new Trip();
		trip.name = "Nappa Valley Unit Test";

		hike.recommendedTrip = trip;
		trip.availableHikes.add( hike );

		entityManager.persist( trip );
		entityManager.persist( hike );

		entityManager.flush();
		entityManager.clear();

		hike = entityManager.find( Hike.class, hike.id );

		assertThat( hike.recommendedTrip ).isNotNull();
		assertThat( hike.recommendedTrip.name ).isEqualTo( "Nappa Valley Unit Test" );

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

	@Test
	public void testClearDatabase() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		Hike hike = new Hike( "San Francisco", "Oakland" );
		Trip trip = new Trip();
		trip.name = "Nappa Valley Unit Test";

		hike.recommendedTrip = trip;
		trip.availableHikes.add( hike );

		entityManager.persist( trip );
		entityManager.persist( hike );

		transaction.commit();

		entityManager.clear();

		transaction = entityManager.getTransaction();
		transaction.begin();

		List<?> all = entityManager.createQuery( "from Hike" ).getResultList();
		for ( Hike object : (List<Hike>) all ) {
			object.recommendedTrip = null;
			entityManager.remove( object );
		}

		all = entityManager.createQuery( "from Trip" ).getResultList();
		for ( Object object : all ) {
			entityManager.remove( object );
		}

		transaction.commit();
	}
}
