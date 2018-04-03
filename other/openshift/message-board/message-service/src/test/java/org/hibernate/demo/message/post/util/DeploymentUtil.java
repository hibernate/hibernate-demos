package org.hibernate.demo.message.post.util;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence21.PersistenceUnitTransactionType;

public class DeploymentUtil {

	@Deployment
	public static WebArchive create() {
		return ShrinkWrap
			.create( WebArchive.class, "message-service.war" )
			.addPackages( true, "org.hibernate.demo.message.post.core" )

			// only for test
			.addPackages( true, "org.hibernate.demo.message.test" )

			.addAsResource( new StringAsset( persistenceXml().exportAsString() ), "META-INF/persistence.xml" )
			.addAsResource( "hotrodclient.properties" )
			.addAsWebInfResource( new File( "src/main/webapp/WEB-INF/jboss-deployment-structure.xml" ) );
	}

	private static PersistenceDescriptor persistenceXml() {
		return Descriptors.create( PersistenceDescriptor.class )
			.version( "2.1" )
			.createPersistenceUnit()
			.name( "primary" )
			.transactionType( PersistenceUnitTransactionType._JTA )
			.provider( "org.hibernate.ogm.jpa.HibernateOgmPersistence" )
			.getOrCreateProperties()
			.createProperty().name( "jboss.as.jpa.providerModule" ).value( "org.hibernate:5.2" ).up()
			.createProperty().name( "hibernate.ogm.datastore.provider" ).value( "infinispan_remote" ).up()
			.createProperty().name( "hibernate.ogm.infinispan_remote.configuration_resource_name" ).value( "hotrodclient.properties" ).up()
			.createProperty().name( "hibernate.ogm.datastore.create_database" ).value( "true" ).up()
			.up().up();
	}
}
