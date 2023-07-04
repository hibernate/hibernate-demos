package org.hibernate.demos.outboxpolling.endpoint;

import java.util.List;

import org.hibernate.demos.outboxpolling.dto.mapper.InputOutputMapper;

import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractCrudEndpoint<E, I, O> {
	static final int PAGE_SIZE = 10;

	@Inject
	InputOutputMapper<E, I, O> mapper;
	@Inject
	PanacheRepository<E> repository;

	protected abstract E newInstance();

	@POST
	public O create(I input) {
		E entity = newInstance();
		mapper.input( entity, input );
		repository.persist( entity );
		return mapper.output( entity );
	}

	@GET
	@Path("/{id}")
	public O retrieve(@RestPath long id) {
		return mapper.output( repository.findByIdOptional( id )
				.orElseThrow( NotFoundException::new ) );
	}

	@GET
	@Path("/all")
	public List<O> retrieveAll(@RestQuery @DefaultValue("0") int page) {
		return mapper.output( repository.findAll()
				.page( Page.of( page, PAGE_SIZE ) )
				.list() );
	}

	@PUT
	@Path("/{id}")
	public O update(@RestPath long id, I input) {
		E entity = repository.findByIdOptional( id ).orElseThrow( NotFoundException::new );
		mapper.input( entity, input );
		return mapper.output( entity );
	}

	@DELETE
	@Path("/{id}")
	public RestResponse<Void> delete(@RestPath long id) {
		return repository.deleteById( id )
				? RestResponse.ok()
				: RestResponse.notFound();
	}

}
