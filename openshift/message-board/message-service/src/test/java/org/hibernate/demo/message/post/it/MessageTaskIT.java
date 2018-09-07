package org.hibernate.demo.message.post.it;

import java.io.File;

import org.hibernate.demo.message.post.util.DeploymentUtil;
import org.hibernate.demo.message.post.util.MavenUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

@RunWith(Arquillian.class)
public class MessageTaskIT {

	@Deployment(name = "wildfly")
	@TargetsContainer("wildfly")
	public static WebArchive doDeploy() {
		return DeploymentUtil.create();
	}

	@Deployment(name = "infinispan", testable = false)
	@TargetsContainer("infinispan")
	public static JavaArchive getInfinispanDeployment() {
		File file = Maven.resolver()
			.resolve( "org.hibernate.demos.messageboard:message-server-task:jar:" + MavenUtils.getProperty("project.version") )
			.withoutTransitivity().asSingleFile();

		return ShrinkWrap.createFromZipFile( JavaArchive.class, file );
	}

	@Test
	public void tryStartInfinispanRemoteWithTask() {
		//TODO: implement test case
	}
}
