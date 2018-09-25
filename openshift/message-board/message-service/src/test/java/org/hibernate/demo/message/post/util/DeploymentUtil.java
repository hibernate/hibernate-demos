package org.hibernate.demo.message.post.util;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class DeploymentUtil {

	public static WebArchive wildFly() {
		return ShrinkWrap
			.create( WebArchive.class, "message-service.war" )
			.addPackages( true, "org.hibernate.demo.message.post.core" )

			// only for test
			.addPackages( true, "org.hibernate.demo.message.test" )

			.addAsResource( "META-INF/persistence.xml" )
			.addAsResource( "hotrodclient.properties" )
			.addAsResource( "message.proto" )
			.addAsWebInfResource( new File( "src/main/webapp/WEB-INF/jboss-deployment-structure.xml" ) );
	}

	public static JavaArchive infinispan() {
		File file = Maven.resolver()
				.resolve( "org.hibernate.demos.messageboard:message-server-task:jar:" + MavenUtils.getProperty( "project.version" ) )
				.withoutTransitivity().asSingleFile();

		return ShrinkWrap.createFromZipFile( JavaArchive.class, file );
	}
}
