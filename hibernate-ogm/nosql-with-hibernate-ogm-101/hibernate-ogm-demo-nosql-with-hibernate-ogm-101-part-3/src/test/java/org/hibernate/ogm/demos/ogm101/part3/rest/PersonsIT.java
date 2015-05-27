/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.model.Person;
import org.hibernate.ogm.demos.ogm101.part3.repo.HikeRepository;
import org.hibernate.ogm.demos.ogm101.part3.rest.mapper.ResourceMapper;
import org.hibernate.ogm.demos.ogm101.part3.rest.model.HikeDocument;
import org.hibernate.ogm.demos.ogm101.part3.rest.resource.Hikes;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Call our {@link HikeManagerApplication} invoking the rest API via Arquillian.
 * <p>
 * Tests the creation of a new {@link Person} and the {@link Hike} he's organizing.
 *
 * @author Gunnar Morling
 *
 */
@RunWith(Arquillian.class)
public class PersonsIT {

	private static final String WEBAPP_SRC = "src/main/webapp/WEB-INF";

	@Deployment(testable = false)
	public static WebArchive create() {
		return ShrinkWrap.create( WebArchive.class, "test.war" )
			.addAsResource( "META-INF/persistence.xml" )
			.addAsWebInfResource( new File( WEBAPP_SRC, "jboss-web.xml" ) )
			.addAsWebInfResource( new File( WEBAPP_SRC, "jboss-deployment-structure.xml" ) )
			.addPackage(HikeManagerApplication.class.getPackage() )
			.addPackage(Hikes.class.getPackage() )
			.addPackage(HikeDocument.class.getPackage() )
			.addPackage(HikeRepository.class.getPackage() )
			.addPackage(Hike.class.getPackage() )
			.addPackage(ResourceMapper.class.getPackage() );
	}

	@Test
	public void createAndGetPerson(@ArquillianResteasyResource( "hike-manager/persons" ) ResteasyWebTarget webTarget) throws Exception {
		// Create a person
		Invocation createPerson = invocationBuilder( webTarget ).buildPost(
				jsonEntity( "{ 'firstName' : 'Saundra', 'lastName' : 'Smith' } " )
		);

		Response response = createPerson.invoke();
		assertEquals( HttpStatus.SC_CREATED, response.getStatus() );

		String location = response.getHeaderString( "Location");
		assertNotNull( location );
		response.close();

		// Get the person
		Invocation getPerson = invocationBuilder( webTarget, "/" + getId( location ) ).buildGet();
		response = getPerson.invoke();
		assertEquals( HttpStatus.SC_OK, response.getStatus() );

		JSONAssert.assertEquals(
				"{ 'firstName' : 'Saundra', 'lastName' : 'Smith' }",
				response.readEntity( String.class ),
				false
		);
		response.close();
	}

	@Test
	public void createPersonAndHike(@ArquillianResteasyResource( "hike-manager" ) ResteasyWebTarget webTarget) throws Exception {
		// Create a person
		Invocation createPerson = invocationBuilder( webTarget, "/persons" ).buildPost(
				jsonEntity( "{ 'firstName' : 'Bob', 'lastName' : 'Stamper' } " )
		);

		Response response = createPerson.invoke();
		assertEquals( HttpStatus.SC_CREATED, response.getStatus() );
		response.close();

		String personLocation = response.getHeaderString( "Location");
		assertNotNull( personLocation );

		// Create a hike
		Invocation createHike = invocationBuilder( webTarget, "/hikes" ).buildPost(
				jsonEntity(
						"{" +
								"'organizer' : '" + personLocation + "', " +
								"'description' : 'My first hike', " +
								"'date' : '2012-04-23', " +
								"'difficulty' : 7.3," +
								"'sections':[" +
										"{ 'start' : 'Pendeen', 'end' : 'St. Yves'}" +
								"]" +
						"}"
				)
		);

		response = createHike.invoke();
		assertEquals( HttpStatus.SC_CREATED, response.getStatus() );
		response.close();

		String hikeLocation = response.getHeaderString( "Location");
		assertNotNull( hikeLocation );

		// Get the person
		Invocation getPerson = invocationBuilder( webTarget, "/persons/" + getId( personLocation ) ).buildGet();
		response = getPerson.invoke();
		assertEquals( HttpStatus.SC_OK, response.getStatus() );

		JSONAssert.assertEquals(
				"{ 'firstName' : 'Bob', 'lastName' : 'Stamper', 'organizedHikes' : ['" + hikeLocation + "'] }",
				response.readEntity( String.class ),
				false
		);

		response.close();

		// Get the hike
		Invocation getHike = invocationBuilder( webTarget, "/hikes/" + getId( hikeLocation ) ).buildGet();
		response = getHike.invoke();
		assertEquals( HttpStatus.SC_OK, response.getStatus() );

		JSONAssert.assertEquals(
				"{" +
						"'organizer' : '" + personLocation + "', " +
						"'description' : 'My first hike', " +
						"'date' : '2012-04-23', " +
						"'difficulty' : 7.3," +
						"'sections':[" +
								"{ 'start' : 'Pendeen', 'end' : 'St. Yves'}" +
						"]" +
				"}",
				response.readEntity( String.class ),
				false
		);

		response.close();
	}

	private String getId(String personLocation) {
		return personLocation.substring( personLocation.lastIndexOf( '/' ) + 1 );
	}

	private Entity<String> jsonEntity(String entity) {
		return Entity.entity( entity.replaceAll( "'", "\"" ) , MediaType.APPLICATION_JSON_TYPE );
	}

	private Invocation.Builder invocationBuilder(ResteasyWebTarget webTarget) {
		return invocationBuilder( webTarget, null );
	}

	private Invocation.Builder invocationBuilder(ResteasyWebTarget webTarget, String path) {
		Invocation.Builder builder = path != null ? webTarget.path( path ).request() : webTarget.request();

		builder.acceptEncoding( "UTF-8" );
		builder.accept( MediaType.APPLICATION_JSON );

		return builder;
	}
}
