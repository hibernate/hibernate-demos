package org.hibernate.demos.hsearchfeatureexamples;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import org.hibernate.demos.hsearchfeatureexamples.dto.SearchResultDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.mapper.TShirtMapper;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirt;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

@Path("/tshirt")
@Transactional
public class TShirtService {

	private static final int PAGE_SIZE = 10;

	@Inject
	TShirtMapper mapper;

	@POST
	public TShirtOutputDto create(TShirtInputDto input) {
		TShirt entity = new TShirt();
		mapper.input( entity, input );
		TShirt.persist( entity );
		return mapper.output( entity );
	}

	@PUT
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

	@GET
	@Path("search")
	public SearchResultDto<TShirtOutputDto> search(@QueryParam String q,
			@QueryParam int page, @QueryParam boolean brief) {
		PanacheQuery<PanacheEntityBase> query =
				TShirt.find( "lower(name) like concat('%', lower(?1), '%')",
						Sort.ascending( "name" ), q );
		query.page( page, PAGE_SIZE );
		return new SearchResultDto<>( query.count(), mapper.output( query.list(), brief ) );
	}


	private TShirt find(long id) {
		TShirt entity = TShirt.findById( id );
		if ( entity == null ) {
			throw new NotFoundException();
		}
		return entity;
	}
}