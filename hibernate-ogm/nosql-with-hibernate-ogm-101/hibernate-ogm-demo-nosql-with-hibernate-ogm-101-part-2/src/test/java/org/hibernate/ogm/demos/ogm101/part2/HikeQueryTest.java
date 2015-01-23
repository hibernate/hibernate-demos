/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part2;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.lucene.search.Query;
import org.hibernate.ogm.demos.ogm101.part2.Hike;
import org.hibernate.ogm.demos.ogm101.part2.HikeSection;
import org.hibernate.ogm.demos.ogm101.part2.Person;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Shows how to persist and retrieve entities to/from NoSQL stores using Hibernate OGM.
 *
 * @author Gunnar Morling
 */
public class HikeQueryTest {

	private static EntityManagerFactory entityManagerFactory;

	@BeforeClass
	public static void setUpEntityManagerFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "hikePu" );

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		// create a Person
		Person bob = new Person( "Bob", "McRobb" );

		// and two hikes
		Hike cornwall = new Hike(
				"Visiting Land's End", new Date(), new BigDecimal( "5.5" ),
				new HikeSection( "Penzance", "Mousehole" ),
				new HikeSection( "Mousehole", "St. Levan" ),
				new HikeSection( "St. Levan", "Land's End" )
		);
		Hike isleOfWight = new Hike(
				"Exploring Carisbrooke Castle", new Date(), new BigDecimal( "7.5" ),
				new HikeSection( "Freshwater", "Calbourne" ),
				new HikeSection( "Calbourne", "Carisbrooke Castle" )
		);

		// let Bob organize the two hikes
		cornwall.setOrganizer( bob );
		bob.getOrganizedHikes().add( cornwall );

		isleOfWight.setOrganizer( bob );
		bob.getOrganizedHikes().add( isleOfWight );

		// persist organizer (will be cascaded to hikes)
		entityManager.persist( bob );

		entityManager.getTransaction().commit();

		// get a new EM to make sure data is actually retrieved from the store and not Hibernate’s internal cache
		entityManager.close();
	}

	@AfterClass
	public static void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}

	@Test
	public void canSearchUsingJPQLQuery() {
		// Get a new entityManager
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Start transaction
		entityManager.getTransaction().begin();

		// Find all the available hikes ordered by difficulty
		List<Hike> hikes = entityManager.createQuery( "SELECT h FROM Hike h ORDER BY h.difficulty ASC" , Hike.class ).getResultList();
		assertThat( hikes.size() ).isEqualTo( 2 );
		assertThat( hikes ).onProperty( "description" ).containsExactly( "Visiting Land's End", "Exploring Carisbrooke Castle" );

		entityManager.getTransaction().commit();

		entityManager.close();
	}

	@Test
	public void canSearchUsingNativeQuery() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		// Search for the hikes with a section that start from "Penzace" in MongoDB
		List<Hike> hikes = entityManager
				.createNativeQuery( "{ $query : { sections : { $elemMatch : { start: 'Penzance' } } } }", Hike.class )
				.getResultList();

		// Search for the hikes with a section that start from "Penzace" in Neo4j
//		List<Hike> hikes = entityManager
//				.createNativeQuery( " MATCH (h:Hike) - - (:Hike_sections {start: 'Penzance'} ) RETURN h", Hike.class )
//				.getResultList();

		assertThat( hikes ).onProperty( "description" ).containsOnly( "Visiting Land's End" );

		entityManager.getTransaction().commit();

		entityManager.close();
	}

	@Test
	public void canSearchUsingFullTextQuery() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		//Add full-text superpowers to any EntityManager:
		FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);

		// Optionally use the QueryBuilder to simplify Query definition:
		QueryBuilder b = ftem.getSearchFactory().buildQueryBuilder().forEntity( Hike.class ).get();

		// A lucene query to look for hike to the Carisbrooke castle:
		// Note that the query looks for "cariboke" instead of "Carisbrooke"
		Query lq = b.keyword().onField("description").matching("carisbroke castle").createQuery();

		//Transform the Lucene Query in a JPA Query:
		FullTextQuery ftQuery = ftem.createFullTextQuery(lq, Hike.class);

		//This is a requirement when using Hibernate OGM instead of ORM:
		ftQuery.initializeObjectsWith( ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID );

		// List matching hikes
		List<Hike> hikes = ftQuery.getResultList();
		assertThat( hikes ).onProperty( "description" ).containsOnly( "Exploring Carisbrooke Castle" );

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
