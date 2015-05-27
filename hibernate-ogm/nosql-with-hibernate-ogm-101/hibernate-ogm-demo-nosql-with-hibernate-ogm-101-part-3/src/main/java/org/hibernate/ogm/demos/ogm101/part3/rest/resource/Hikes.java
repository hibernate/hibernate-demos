/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.repo.HikeRepository;
import org.hibernate.ogm.demos.ogm101.part3.rest.mapper.ResourceMapper;
import org.hibernate.ogm.demos.ogm101.part3.rest.mapper.UriMapper;
import org.hibernate.ogm.demos.ogm101.part3.rest.model.HikeDocument;

/**
 * REST resource for managing {@link Hike}s.
 *
 * @author Gunnar Morling
 */
@Path("/hikes")
@Stateless
@Produces("application/json")
@Consumes("application/json")
public class Hikes {

	@Inject
	private HikeRepository hikeRepository;

	@Inject
	private ResourceMapper mapper;

	@Inject
	private UriMapper uris;

	public Hikes() {
	}

	@GET
	@Path("/")
	public Response listHikes() {
		List<Hike> hikes = hikeRepository.getAll();
		List<HikeDocument> hikeDocuments = mapper.toHikeDocuments( hikes );

		return Response.ok( hikeDocuments ).build();
	}

	@POST
	@Path("/")
	public Response createHike(HikeDocument request) throws Exception {
		Hike hike = hikeRepository.create( mapper.toHike( request ) );
		return Response.created( uris.toUri( hike ) ).build();
	}

	@GET
	@Path("/{id}")
	public Response getHike(@PathParam("id") String id) {
		Hike hike = hikeRepository.get( id );
		if ( hike == null ) {
			return Response.status( Status.NOT_FOUND ).build();
		}
		else {
			return Response.ok( mapper.toHikeDocument( hike ) ).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response updateHike(HikeDocument request, @PathParam("id") String id) {
		Hike hike = hikeRepository.get( id );
		if ( hike == null ) {
			return Response.status( Status.NOT_FOUND ).build();
		}

		mapper.updateHike( request, hike );

		if ( hike.getOrganizer() != null ) {
			hike.getOrganizer().getOrganizedHikes().add( hike );
		}

		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") String id) {
		hikeRepository.remove( new Hike( id ) );
		return Response.ok().build();
	}
}
