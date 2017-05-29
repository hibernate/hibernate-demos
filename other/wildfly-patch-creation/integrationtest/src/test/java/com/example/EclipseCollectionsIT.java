/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package com.example;

import static org.junit.Assert.assertEquals;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gunnar Morling
 */
@RunWith(Arquillian.class)
public class EclipseCollectionsIT {

	@Deployment
	public static WebArchive createTestArchive() throws Exception {
		return ShrinkWrap.create( WebArchive.class, "mywar.war" )
				.addAsWebInfResource( "jboss-deployment-structure.xml" );
	}

	@Test
	public void canUseEclipseCollections() {
		ImmutableList<String> names = Lists.immutable.of( "Bob", "Alice", "Sarah" );
		assertEquals( 3, names.size() );
	}
}
