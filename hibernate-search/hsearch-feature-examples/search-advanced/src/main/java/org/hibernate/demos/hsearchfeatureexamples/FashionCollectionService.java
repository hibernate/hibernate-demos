package org.hibernate.demos.hsearchfeatureexamples;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

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

	@PUT
	public FashionCollectionOutputDto create(FashionCollectionInputDto input) {
		FashionCollection entity = new FashionCollection();
		mapper.input( entity, input );
		FashionCollection.persist( entity );
		return mapper.output( entity );
	}

	@POST
	@Path("{id}")
	public FashionCollectionOutputDto update(@PathParam long id, FashionCollectionInputDto input) {
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
		List<FashionCollection> entities = FashionCollection.findAll().page( page, PAGE_SIZE ).list();
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