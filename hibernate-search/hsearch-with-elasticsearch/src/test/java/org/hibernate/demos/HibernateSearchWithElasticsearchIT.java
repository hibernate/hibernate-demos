/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.demos.hswithes.model.Character;
import org.hibernate.demos.hswithes.model.Publisher;
import org.hibernate.demos.hswithes.model.VideoGame;
import org.hibernate.search.backend.elasticsearch.ElasticsearchQueries;
import org.hibernate.search.backend.elasticsearch.ProjectionConstants;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.BasicTransformerAdapter;
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
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword().onField( "title" ).matching( "samurai" ).createQuery(),
					VideoGame.class
			);

			List<VideoGame> videoGames = query.getResultList();
			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai" );
		} );

		em.close();
	}

	@Test
	public void queryOnMultipleFields() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword().onFields( "title", "description").matching( "samurai" ).createQuery(),
					VideoGame.class
			);

			List<VideoGame> videoGames = query.getResultList();
			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void wildcardQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword().wildcard().onFields( "title", "description").matching( "sam*" ).createQuery(),
					VideoGame.class
			);

			List<VideoGame> videoGames = query.getResultList();
			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void rangeQuery() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.range()
					.onField( "rating" )
					.from( 2 )
					.to( 9 )
					.createQuery(),
					VideoGame.class
			);

			List<VideoGame> videoGames = query.getResultList();
			assertThat( videoGames ).onProperty( "title" ).containsOnly( "Revenge of the Samurai", "Ninja Castle" );
		} );

		em.close();
	}

	@Test
	public void queryString() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					ElasticsearchQueries.fromQueryString( "title:sam* OR description:sam*" ),
					VideoGame.class
			);

			List<VideoGame> videoGames = query.getResultList();
			assertThat( videoGames ).onProperty( "title" ).containsExactly( "Revenge of the Samurai", "Tanaka's return" );
		} );

		em.close();
	}

	@Test
	public void projection() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword()
					.onField( "tags" )
					.matching( "round-based" )
					.createQuery(),
					VideoGame.class
			)
			.setProjection( "title", "publisher.name", "release" );

			Object[] projection = (Object[]) query.getSingleResult();

			assertThat( projection[0] ).isEqualTo( "Tanaka's return" );
			assertThat( projection[1] ).isEqualTo( "Samurai Games, Inc." );
			assertThat( projection[2] ).isEqualTo( new GregorianCalendar( 2011, 2, 13 ).getTime() );

			query = ftem.createFullTextQuery(
					qb.keyword()
					.onField( "tags" )
					.matching( "round-based" )
					.createQuery(),
					VideoGame.class
			)
			.setProjection( ProjectionConstants.SCORE, ProjectionConstants.SOURCE );

			projection = (Object[]) query.getSingleResult();

			System.out.println( Arrays.toString( projection ) );
		} );

		em.close();
	}

	@Test
	public void projectionWithTransformer() {
		EntityManager em = emf.createEntityManager();

		inTransaction( em, tx -> {
			FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
			QueryBuilder qb = ftem.getSearchFactory()
					.buildQueryBuilder()
					.forEntity( VideoGame.class )
					.get();

			FullTextQuery query = ftem.createFullTextQuery(
					qb.keyword()
					.onField( "tags" )
					.matching( "round-based" )
					.createQuery(),
					VideoGame.class
			)
			.setProjection( "title", "publisher.name", "release" )
			.setResultTransformer( new BasicTransformerAdapter() {
				@Override
				public VideoGameDto transformTuple(Object[] tuple, String[] aliases) {
					return new VideoGameDto( (String) tuple[0], (String) tuple[1], (Date) tuple[2] );
				}
			} );

			VideoGameDto projection = (VideoGameDto) query.getSingleResult();
			assertThat( projection.getTitle() ).isEqualTo( "Tanaka's return" );
			assertThat( projection.getPublisherName() ).isEqualTo( "Samurai Games, Inc." );
			assertThat( projection.getRelease() ).isEqualTo( new GregorianCalendar( 2011, 2, 13 ).getTime() );
		} );

		em.close();
	}

	@Test
	@Ignore
	public void manualIndexing() {
		EntityManager em = emf.createEntityManager();

		FullTextEntityManager ftem = Search.getFullTextEntityManager( em );
		ftem.getTransaction().begin();

		VideoGame game = ftem.find( VideoGame.class, 311 );
		ftem.index( game );

		ftem.getTransaction().commit();
	}

	@AfterClass
	public static void closeEmf() {
		emf.close();
	}

	public static class VideoGameDto {

		private String title;
		private String publisherName;
		private Date release;

		public VideoGameDto(String title, String publisherName, Date release) {
			this.title = title;
			this.publisherName = publisherName;
			this.release = release;
		}

		public String getTitle() {
			return title;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public Date getRelease() {
			return release;
		}

		@Override
		public String toString() {
			return "VideoGameDto [title=" + title + ", publisherName=" + publisherName + ", release=" + release + "]";
		}
	}
}
