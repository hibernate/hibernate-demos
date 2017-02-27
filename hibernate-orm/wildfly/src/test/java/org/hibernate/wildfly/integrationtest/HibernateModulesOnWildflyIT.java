/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.wildfly.integrationtest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Integration test for using the current Hibernate ORM version on WildFly.
 * <p>
 * Gradle will unzip the targeted WildFly version and unpack the module ZIP created by this build into the server's
 * module directory. Arquillian is used to start this WildFly instance, run this test on the server and stop the server
 * again.
 *
 * @author Gunnar Morling
 */
@RunWith(Arquillian.class)
public class HibernateModulesOnWildflyIT {

	/**
	 * Creates the deployment.
	 *
	 * @return the web archive
	 */
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create( WebArchive.class )
				.addClass( Kryptonite.class )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" )
				.addAsResource( "META-INF/persistence.xml" );
	}

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Should use hibernate orm 52.
	 */
	@Test
	@Transactional(value=TransactionMode.ROLLBACK)
	public void shouldUseHibernateOrm52() {
		Session session = entityManager.unwrap( Session.class );

		Kryptonite kryptonite1 = new Kryptonite();
		kryptonite1.id = 1L;
		kryptonite1.description = "Some Kryptonite";
		session.persist( kryptonite1 );

		session.flush();
		session.clear();

		// EntityManager methods exposed through Session only as of 5.2
		Kryptonite loaded = session.find( Kryptonite.class, 1L );

		assertThat( loaded.description, equalTo( "Some Kryptonite" ) );
	}
}
