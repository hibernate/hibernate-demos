package org.hibernate.ogm.hiking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.repository.HikeRepository;
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
					"Dependencies: org.hibernate:ogm services, org.hibernate.ogm.mongodb services" ),
					"META-INF/MANIFEST.MF"
			);
	}

	@Test
	public void shouldPersistHikeWithOrganizer() {
		Hike hike = hikeRepository.createHike( new Hike( "Land's End", "Bristol" ), new Person( "Bob" ) );
		assertEquals( "Bob", hike.organizer.name );

		entityManager.flush();

		hike = entityManager.find( Hike.class, hike.id );
		assertNotNull( hike );
	}
}
