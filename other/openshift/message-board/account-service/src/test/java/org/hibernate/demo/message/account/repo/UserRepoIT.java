/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.repo;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;

import org.hibernate.demo.message.account.core.entity.User;
import org.hibernate.demo.message.account.core.repo.UserRepo;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceUnitTransactionType;

import static org.hibernate.demo.message.test.util.TestUtil.inTransaction;
import static org.junit.Assert.assertNotNull;


/**
 * @author Andrea Boriero
 */
@RunWith(Arquillian.class)
public class UserRepoIT {
	@Deployment
	public static WebArchive create() {
		return ShrinkWrap
				.create( WebArchive.class, "account-service.war" )
				.addPackages( true, "org.hibernate.demo.message.account.core" )

				// only for test
				.addPackages( true, "org.hibernate.demo.message.test" )

				.addAsResource( new StringAsset( persistenceXml().exportAsString() ), "META-INF/persistence.xml" );
	}

	private static PersistenceDescriptor persistenceXml() {
		return Descriptors.create( PersistenceDescriptor.class )
				.version( "2.1" )
				.createPersistenceUnit()
				.name( "primary" )
				.transactionType( PersistenceUnitTransactionType._JTA )
				.jtaDataSource( "java:jboss/datasources/ExampleDS" )
				.getOrCreateProperties()
				.createProperty().name( "hibernate.hbm2ddl.auto" ).value( "create-drop" ).up()
				.createProperty().name( "hibernate.allow_update_outside_transaction" ).value( "true" ).up()
				.createProperty().name( "jboss.as.jpa.providerModule" ).value( "org.hibernate:5.2" ).up()
				.up().up();
	}

	@Inject
	private UserRepo repo;

	@Inject
	private UserTransaction utx;

	@Test
	public void testCreateAUser() {
		User user = new User( "hibernate" );
		inTransaction( utx, ut -> {
			repo.add( user );
		} );

		assertNotNull( user.getId() );
	}

	@Test
	public void testDeleteAllUsers(){
		User user = new User( "hibernate" );
		inTransaction( utx, ut -> {
			repo.add( user );
		} );

		assertNotNull( user.getId() );

		inTransaction( utx, ut -> {
			repo.deleteAll();
		} );
	}

	@Test(expected = NoResultException.class)
	public void testFindByUserNameOfNoExistingUser() {
		String userName = "hibernate";
		inTransaction( utx, ut -> {
			User result = repo.findByUserName( userName );
			assertNotNull( result );
		} );
	}

	@After
	public void tearDown() {
		inTransaction( utx, ut -> {
			repo.deleteAll();
		} );
	}

}
