package org.hibernate.demo.message.account.repo.service.util;

import org.hibernate.Session;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceUnitTransactionType;

public class DeploymentUtil {

	private static final String ORM_VERSION = Session.class.getPackage().getImplementationVersion();
	private static final String ORM_MINOR_VERSION = ORM_VERSION.substring( 0, ORM_VERSION.indexOf( ".", ORM_VERSION.indexOf( "." ) + 1 ) );

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
				// We want to use the ORM from this build instead of the one coming with WildFly
//				.createProperty().name( "jboss.as.jpa.providerModule" ).value( "org.hibernate:" + ORM_MINOR_VERSION ).up()
				.createProperty().name( "hibernate.hbm2ddl.auto" ).value( "create-drop" ).up()
				.up().up();
	}
}
