package org.hibernate.demos.outboxpolling.endpoint;

import java.util.List;
import java.util.Map;

import org.hibernate.demos.outboxpolling.dto.BookInputDto;
import org.hibernate.demos.outboxpolling.dto.BookOutputDto;
import org.hibernate.demos.outboxpolling.dto.SearchResultDto;
import org.hibernate.demos.outboxpolling.model.Book;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.util.common.data.Range;

import org.jboss.resteasy.reactive.RestQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/book")
public class BookEndpoint
		extends AbstractCrudEndpoint<Book, BookInputDto, BookOutputDto> {
	@Inject
	SearchSession searchSession;

	@Override
	protected Book newInstance() {
		return new Book();
	}

	@GET
	@Path("search")
	public SearchResultDto<BookOutputDto> search(@RestQuery("q") String query,
			@RestQuery @DefaultValue("0") int page) {
		return mapper.searchOutput(
				searchSession.search( Book.class )
						.where( f -> query == null || query.isBlank()
								? f.matchAll()
								: f.simpleQueryString()
								.fields( "title",
										"authors.firstName", "authors.lastName" )
								.matching( query )
								.defaultOperator( BooleanOperator.AND ) )
						.sort( f -> f.field( "title_sort" ) )
						.fetch( page * PAGE_SIZE, PAGE_SIZE )
		);
	}

	@GET
	@Path("search/facets")
	public SearchResultDto<BookOutputDto> search_facets(@RestQuery("q") String query,
			@RestQuery @DefaultValue("0") int page) {
		AggregationKey<Map<String, Long>> countByGenre =
				AggregationKey.of( "count-by-genre" );
		AggregationKey<Map<Range<Integer>, Long>> countByPageCount =
				AggregationKey.of( "count-by-page-count" );

		return mapper.searchOutput(
				searchSession.search( Book.class )
						.where( f -> query == null || query.isBlank()
								? f.matchAll()
								: f.simpleQueryString()
								.fields( "title",
										"authors.firstName", "authors.lastName" )
								.matching( query )
								.defaultOperator( BooleanOperator.AND ) )
						.sort( f -> f.field( "title_sort" ) )
						.aggregation( countByGenre, f -> f.terms()
								.field( "genres", String.class )
								.orderByTermAscending()
								.minDocumentCount( 0 ) )
						.aggregation( countByPageCount, f -> f.range()
								.field( "pageCount", Integer.class )
								.range( 0, 100 )
								.range( 100, 200 )
								.range( 200, 400 )
								.range( 400, 600 )
								.range( 600, null ) )
						.fetch( page * PAGE_SIZE, PAGE_SIZE ),
				List.of( countByGenre, countByPageCount )
		);
	}
}
