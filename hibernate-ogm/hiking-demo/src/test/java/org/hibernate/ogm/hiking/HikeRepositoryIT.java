package org.hibernate.ogm.hiking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.model.Trip;
import org.hibernate.ogm.hiking.repository.HikeRepository;
import org.hibernate.ogm.hiking.repository.TripRepository;
import org.hibernate.validator.internal.util.Version;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Transactional
public class HikeRepositoryIT {

	private static final String WEBAPP_SRC = "src/main/webapp/";

	@Inject
	private HikeRepository hikeRepository;

	@Inject
	private TripRepository tripRepository;

	@PersistenceContext(unitName="hike-PU-JTA")
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
			.create( WebArchive.class, HikeRepositoryIT.class.getSimpleName() + ".war" )
			.addPackage( Hike.class.getPackage() )
			.addPackage( HikeRepository.class.getPackage() )
			.addAsResource( "META-INF/persistence.xml" )
			.addAsWebInfResource( new File( WEBAPP_SRC + "WEB-INF/beans.xml" ) )
			.addAsResource( new StringAsset(
					"Dependencies: org.hibernate.ogm:main services, org.hibernate.ogm.mongodb:main services" ),
					"META-INF/MANIFEST.MF"
			);
	}

	@Test
	public void shouldPersistHikeWithRecommendedTrip() {
		Trip trip = new Trip();
		trip.name = "test";
		trip = tripRepository.createTrip( trip );
		Hike hike = hikeRepository.createHike( new Hike( "Land's End", "Bristol" ), trip );
		assertEquals( "test", hike.recommendedTrip.name );

		entityManager.flush();

		hike = entityManager.find( Hike.class, hike.id );
		assertNotNull( hike );
	}

	@Test
	public void testNativeQueries() {
		Trip trip = new Trip();
		trip.name = "End of the world";
		trip = tripRepository.createTrip( trip );
		Hike hike = hikeRepository.createHike( new Hike( "North pole", "South pole" ), trip );

		entityManager.flush();
		entityManager.clear();

		List<Hike> hikes = hikeRepository.getHikesByTripId( trip.id );
		assertEquals( 1, hikes.size() );
	}

	@Test
	public void testValidation() {
		assertEquals( "6.0.0.Alpha1", Version.getVersionString() );

	}
}
