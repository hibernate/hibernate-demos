package org.hibernate.search.demos.wikipedia.endpoint;

import java.net.URI;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.dao.PageDao;
import org.hibernate.search.demos.wikipedia.data.dao.PageSort;
import org.hibernate.search.demos.wikipedia.dto.PageInputDto;
import org.hibernate.search.demos.wikipedia.dto.PageOutputDto;
import org.hibernate.search.demos.wikipedia.dto.mapper.PageMapper;
import org.hibernate.search.demos.wikipedia.util.SearchResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Path("/page")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional(readOnly = true)
public class PageEndpoint {
	
	private static final int ITEMS_PER_PAGE = 20;
	
	@Inject
	private PageDao dao;

	@POST
	@Path("/")
	@Transactional(readOnly = false)
	public Response create(PageInputDto pageDto, @Context UriInfo uriInfo) {
		Page page = new Page();
		PageMapper.INSTANCE.input( page, pageDto );
		dao.create( page );
		URI pageUri = uriInfo.getAbsolutePathBuilder().path( "{id}" ).build( page.getId() );
		return Response.created( pageUri ).build();
	}

	@PUT
	@Path("/{id}")
	@Transactional(readOnly = false)
	public Response update(@PathParam("id") Long id, PageInputDto pageDto) {
		Page page = getExisting( id );
		PageMapper.INSTANCE.input( page, pageDto );
		dao.update( page );
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@Transactional(readOnly = false)
	public void delete(@PathParam("id") Long id) {
		dao.delete( getExisting( id ) );
	}

	@GET
	@Path("/{id}")
	public PageOutputDto getById(@PathParam("id") Long id) {
		return PageMapper.INSTANCE.output( getExisting( id ) );
	}

	@GET
	@Path("/search")
	public SearchResult<PageOutputDto> search(@QueryParam("q") String queryString,
			@QueryParam("s") @DefaultValue( "RELEVANCE" ) PageSort sort,
			@QueryParam("p") @DefaultValue( "0" ) int page) {
		SearchResult<Page> searchResult = dao.search(
				queryString, sort,
				page * ITEMS_PER_PAGE, ITEMS_PER_PAGE
		);
		
		return new SearchResult<>(
				searchResult.getTotalCount(),
				searchResult.getHits()
						.stream()
						.map( PageMapper.INSTANCE::outputSummary )
						.collect( Collectors.toList() )
		);
	}

	private Page getExisting(Long id) {
		Page page = dao.getById( id );
		if ( page == null ) {
			throw new NotFoundException();
		}
		return page;
	}

}
