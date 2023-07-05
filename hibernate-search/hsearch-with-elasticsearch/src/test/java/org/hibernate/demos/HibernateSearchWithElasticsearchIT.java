/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.demos.hswithes.dto.VideoGameDto;
import org.hibernate.demos.hswithes.model.Character;
import org.hibernate.demos.hswithes.model.Publisher;
import org.hibernate.demos.hswithes.model.VideoGame;
import org.hibernate.search.backend.elasticsearch.ElasticsearchExtension;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class HibernateSearchWithElasticsearchIT extends TestBase {

	private static EntityManagerFactory emf;

	@BeforeClass
	public static void setUpEmf() {
		emf = Persistence.createEntityManagerFactory( "videoGamePu" );
		setUpTestData();
	}

	public static void setUpTestData() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			Publisher samuraiGames = new Publisher( "Samurai Games, Inc.", "12 Main Road" );

			Character luigi = new Character( "Luigi", "Plumbing" );
			em.persist( luigi );

			Character frank = new Character( "Frank", "Sleeping" );
			em.persist( frank );

			Character dash = new Character( "Dash", "Running" );
			em.persist( dash );

			// Game 1
			VideoGame game = new VideoGame.Builder()
					.withTitle( "Revenge of the Samurai" )
					.withDescription( "The Samurai is mad and takes revenge" )
					.withRating( 8 )
					.withPublishingDate( new GregorianCalendar( 2005, 11, 5 ).getTime() )
					.withPublisher( samuraiGames )
					.withCharacters( luigi, dash )
					.withTags( "action", "real-time", "anime" )
					.build();

			em.persist( game );

			// Game 2
			game = new VideoGame.Builder()
					.withTitle( "Tanaka's return" )
					.withDescription( "The famous Samurai Tanaka returns" )
					.withRating( 10 )
					.withPublishingDate( new GregorianCalendar( 2011, 2, 13 ).getTime() )
					.withPublisher( samuraiGames )
					.withCharacters( frank, dash, luigi )
					.withTags( "action", "round-based" )
					.build();

			em.persist( game );

			// Game 3
			game = new VideoGame.Builder()
					.withTitle( "Ninja Castle" )
					.withDescription( "7 Ninjas live in a castle" )
					.withRating( 5 )
					.withPublishingDate( new GregorianCalendar( 2007, 3, 7 ).getTime() )
					.withPublisher( samuraiGames )
					.withCharacters( frank )
					.build();

			em.persist( game );

		} );

		em.close();
	}

	@Test
	public void queryOnSingleField() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			List<VideoGame> videoGames = searchSession.search( VideoGame.class )
					.where( f -> f.match().field( "title" )
							.matching( "samurai" ) )
					.fetchHits( 20 );

			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai" );
		} );

		em.close();
	}

	@Test
	public void queryOnMultipleFields() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			List<VideoGame> videoGames = searchSession.search( VideoGame.class )
					.where( f -> f.match().fields( "title", "description" )
							.matching( "samurai" ) )
					.fetchHits( 20 );

			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void wildcardQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			List<VideoGame> videoGames = searchSession.search( VideoGame.class )
					.where( f -> f.wildcard().fields( "title", "description" )
							.matching( "sam*" ) )
					.fetchHits( 20 );

			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void rangeQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			List<VideoGame> videoGames = searchSession.search( VideoGame.class )
					.where( f -> f.range().field( "rating" )
							.between( 2, 9 ) )
					.fetchHits( 20 );

			assertThat( videoGames ).onProperty( "title" ).containsOnly( "Revenge of the Samurai", "Ninja Castle" );
		} );

		em.close();
	}

	@Test
	public void queryString() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			List<VideoGame> videoGames = searchSession.search( VideoGame.class )
					.where( f -> f.simpleQueryString().fields( "title", "description" )
							.matching( "Tanaka | Castle" ) )
					.fetchHits( 20 );

			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Ninja Castle", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void projection() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			SearchSession searchSession = Search.session( em );

			VideoGameDto projection = searchSession.search( VideoGame.class )
					.select( VideoGameDto.class )
					.where( f -> f.match().field( "tags" ).matching( "round-based" ) )
					.fetchSingleHit().orElseThrow();

			assertThat( projection.getTitle() ).isEqualTo( "Tanaka's return" );
			assertThat( projection.getPublisherName() ).isEqualTo( "Samurai Games, Inc." );
			assertThat( projection.getRelease() ).isEqualTo( new GregorianCalendar( 2011, 2, 13 ).getTime() );

			Object[] scoreAndSource = searchSession.search( VideoGame.class )
					.extension( ElasticsearchExtension.get() )
					.select( f -> f.composite().from( f.score(), f.source() ).asArray() )
					.where( f -> f.match().field( "tags" ).matching( "round-based" ) )
					.fetchSingleHit().orElseThrow();

			System.out.println( Arrays.toString( scoreAndSource ) );
		} );

		em.close();
	}

	@Test
	@Ignore
	public void manualIndexing() {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		SearchSession searchSession = Search.session( em );

		VideoGame game = em.find( VideoGame.class, 311 );
		searchSession.indexingPlan().addOrUpdate( game );

		em.getTransaction().commit();
	}

	@AfterClass
	public static void closeEmf() {
		if ( emf != null ) {
			emf.close();
		}
	}

}
