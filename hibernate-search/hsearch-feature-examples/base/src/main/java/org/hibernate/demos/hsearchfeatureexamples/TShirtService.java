package org.hibernate.demos.hsearchfeatureexamples;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.mapper.TShirtMapper;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirt;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/tshirt")
@Transactional
public class TShirtService {

	private static final int PAGE_SIZE = 10;

	@Inject
	TShirtMapper mapper;

	@PUT
	public TShirtOutputDto create(TShirtInputDto input) {
		TShirt entity = new TShirt();
		mapper.input( entity, input );
		TShirt.persist( entity );
		return mapper.output( entity );
	}

	@POST
	@Path("{id}")
	public TShirtOutputDto update(@PathParam long id, TShirtInputDto input) {
		TShirt entity = find( id );
		mapper.input( entity, input );
		return mapper.output( entity );
	}

	@GET
	@Path("{id}")
	public TShirtOutputDto retrieve(@PathParam long id) {
		TShirt entity = find( id );
		return mapper.output( entity );
	}

	@GET
	public List<TShirtOutputDto> list(@QueryParam int page, @QueryParam boolean brief) {
		List<TShirt> entities = TShirt.findAll().page( page, PAGE_SIZE ).list();
		return mapper.output( entities, brief );
	}

	private TShirt find(long id) {
		TShirt entity = TShirt.findById( id );
		if ( entity == null ) {
			throw new NotFoundException();
		}
		return entity;
	}
}