/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.hibernate.ogm.demos.ogm101.part3.model.Person;
import org.hibernate.ogm.demos.ogm101.part3.repo.PersonRepository;
import org.hibernate.ogm.demos.ogm101.part3.rest.mapper.ResourceMapper;
import org.hibernate.ogm.demos.ogm101.part3.rest.mapper.UriMapper;
import org.hibernate.ogm.demos.ogm101.part3.rest.model.PersonDocument;

/**
 * REST resource for managing {@link Person}s.
 *
 * @author Gunnar Morling
 */
@Path("/persons")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class Persons {

	@Inject
	private PersonRepository personRepository;

	@Inject
	private ResourceMapper mapper;

	@Inject
	private UriMapper uris;

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/")
	public Response listPersons() {
		List<Person> persons = personRepository.getAll();
		List<PersonDocument> personDocuments = mapper.toPersonDocuments( persons );

		return Response.ok( personDocuments ).build();
	}

	@POST
	@Path("/")
	public Response createPerson(PersonDocument request) {
		Person person = personRepository.create( mapper.toPerson( request ) );
		return Response.created( uris.toUri( person ) ).build();
	}

	@GET
	@Path("/{id}")
	public Response getPerson(@PathParam("id") String id) {
		Person person = personRepository.get( id );
		if ( person == null ) {
			return Response.status( Status.NOT_FOUND ).build();
		}
		else {
			return Response.ok( mapper.toPersonDocument( person ) ).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response updatePerson(PersonDocument request, @PathParam("id") String id) {
		Person person = personRepository.get( id );
		if ( person == null ) {
			return Response.status( Status.NOT_FOUND ).build();
		}

		mapper.updatePerson( request, person );

		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") String id) {
		personRepository.remove( new Person( id ) );
		return Response.ok().build();
	}

	// Consumed by ResourceMapper
	@RequestScoped
	@javax.enterprise.inject.Produces
	public UriInfo produceUriInfo() {
		return uriInfo;
	}
}
