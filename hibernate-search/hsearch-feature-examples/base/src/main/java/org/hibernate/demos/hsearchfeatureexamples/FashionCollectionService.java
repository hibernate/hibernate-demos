package org.hibernate.demos.hsearchfeatureexamples;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import org.hibernate.demos.hsearchfeatureexamples.dto.FashionCollectionInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.FashionCollectionOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.mapper.FashionCollectionMapper;
import org.hibernate.demos.hsearchfeatureexamples.model.FashionCollection;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/collection")
@Transactional
public class FashionCollectionService {

	private static final int PAGE_SIZE = 10;

	@Inject
	FashionCollectionMapper mapper;

	@POST
	public FashionCollectionOutputDto create(FashionCollectionInputDto input) {
		FashionCollection entity = new FashionCollection();
		mapper.input( entity, input );
		FashionCollection.persist( entity );
		return mapper.output( entity );
	}

	@PUT
	@Path("{id}")
	public FashionCollectionOutputDto update(@PathParam long id,
			FashionCollectionInputDto input) {
		FashionCollection entity = find( id );
		mapper.input( entity, input );
		return mapper.output( entity );
	}

	@GET
	@Path("{id}")
	public FashionCollectionOutputDto retrieve(@PathParam long id) {
		FashionCollection entity = find( id );
		return mapper.output( entity );
	}

	@GET
	public List<FashionCollectionOutputDto> list(@QueryParam int page) {
		List<FashionCollection> entities = FashionCollection.findAll()
				.page( page, PAGE_SIZE ).list();
		return mapper.output( entities );
	}

	private FashionCollection find(long id) {
		FashionCollection entity = FashionCollection.findById( id );
		if ( entity == null ) {
			throw new NotFoundException();
		}
		return entity;
	}
}