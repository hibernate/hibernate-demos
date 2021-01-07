package org.hibernate.demos.hsearchfeatureexamples;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.hibernate.demos.hsearchfeatureexamples.dto.PriceRangeDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.SearchResultDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.mapper.TShirtMapper;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirt;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirtSize;
import org.hibernate.search.backend.elasticsearch.ElasticsearchExtension;
import org.hibernate.search.backend.elasticsearch.search.query.ElasticsearchSearchResult;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.util.common.data.Range;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.glassfish.json.JsonUtil;

@Path("/tshirt")
@Transactional
public class TShirtService {

	private static final int PAGE_SIZE = 10;

	@Inject
	TShirtMapper mapper;

	@Inject
	SearchSession searchSession;

	private final Gson gson = new Gson();

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

	@GET
	@Path("search")
	public SearchResultDto<TShirtOutputDto> search(@QueryParam String q,
			@QueryParam int page, @QueryParam boolean brief) {
		SearchResult<TShirt> result = searchSession.search( TShirt.class )
				.where( f -> {
					if ( q == null || q.isBlank() ) {
						return f.matchAll();
					}
					else {
						return f.simpleQueryString()
								.fields( "name", "collection.keywords",
										"variants.color", "variants.size"
								)
								.matching( q );
					}
				} )
				.fetch( page * PAGE_SIZE, PAGE_SIZE );

		return new SearchResultDto<>( result.total().hitCount(), mapper.output( result.hits(), brief ) );
	}

	@GET
	@Path("autocomplete")
	public List<TShirtOutputDto> autocomplete(@QueryParam String terms) {
		List<TShirt> hits = searchSession.search( TShirt.class )
				.where( f -> f.simpleQueryString()
						.fields( "name_autocomplete" )
						.matching( terms )
						.defaultOperator( BooleanOperator.AND ) )
				.fetchHits( PAGE_SIZE );

		return mapper.outputBrief( hits );
	}

	@GET
	@Path("autocomplete_nodb")
	public List<TShirtOutputDto> autocompleteNoDatabase(@QueryParam String terms) {
		return searchSession.search( TShirt.class )
				.select( f -> f.composite(
						(ref, field) -> TShirtOutputDto.of( (long) ref.id(), field ),
						f.entityReference(), f.field( "name", String.class )
				) )
				.where( f -> f.simpleQueryString()
						.fields( "name_autocomplete" )
						.matching( terms )
						.defaultOperator( BooleanOperator.AND ) )
				.fetchHits( PAGE_SIZE );
	}

	@GET
	@Path("search_facets")
	public SearchResultDto<TShirtOutputDto> searchWithFacets(@QueryParam String q,
			@QueryParam String color, @QueryParam TShirtSize size,
			@QueryParam PriceRangeDto priceRange,
			@QueryParam int page, @QueryParam boolean brief) {
		AggregationKey<Map<String, Long>> countByColor = AggregationKey.of( "count-by-color" );
		AggregationKey<Map<TShirtSize, Long>> countBySize = AggregationKey.of( "count-by-size" );
		AggregationKey<Map<Range<BigDecimal>, Long>> countByPriceRange = AggregationKey.of( "count-by-price-range" );

		SearchResult<TShirt> result = searchSession.search( TShirt.class )
				.where( f -> f.bool( b -> {
					b.must( f.matchAll() ); // Match all by default

					if ( q != null && !q.isBlank() ) {
						b.must( f.simpleQueryString()
								.fields( "name", "collection.keywords",
										"variants_nested.color", "variants_nested.size"
								)
								.matching( q ) );
					}

					if ( color != null || size != null || priceRange != null ) {
						b.must( f.nested().objectField( "variants_nested" )
								.nest( variantFilter( f, size, color, priceRange ) ) );
					}
				} ) )
				.aggregation( countByColor, f -> f.terms()
						.field( "variants_nested.color_keyword", String.class )
						.filter( f2 -> variantFilter( f2, size, color, priceRange ) )
						.orderByTermAscending()
						.minDocumentCount( 0 ) )
				.aggregation( countBySize, f -> f.terms()
						.field( "variants_nested.size_keyword", TShirtSize.class )
						.filter( f2 -> variantFilter( f2, size, color, priceRange ) )
						.orderByTermAscending()
						.minDocumentCount( 0 ) )
				.aggregation( countByPriceRange, f -> f.range()
						.field( "variants_nested.price", BigDecimal.class )
						.ranges( EnumSet.allOf( PriceRangeDto.class ).stream().map( r -> r.value )
								.collect( Collectors.toList() ) )
						.filter( f2 -> variantFilter( f2, size, color, priceRange ) ) )
				.fetch( page * PAGE_SIZE, PAGE_SIZE );

		return new SearchResultDto<>( result.total().hitCount(), mapper.output( result.hits(), brief ),
				Map.of(
						countByColor.name(), result.aggregation( countByColor ),
						countBySize.name(), result.aggregation( countBySize ),
						countByPriceRange.name(), result.aggregation( countByPriceRange )
				)
		);
	}

	private PredicateFinalStep variantFilter(SearchPredicateFactory f, TShirtSize size, String color,
			PriceRangeDto priceRange) {
		if ( size == null && color == null && priceRange == null ) {
			return f.matchAll();
		}
		return f.bool( variantsBool -> {
			if ( color != null ) {
				variantsBool.must( f.match().field( "variants_nested.color" ).matching( color ) );
			}
			if ( size != null ) {
				variantsBool.must( f.match().field( "variants_nested.size" ).matching( size ) );
			}
			if ( priceRange != null ) {
				variantsBool.must( f.range().field( "variants_nested.price" ).range( priceRange.value ) );
			}
		} );
	}

	@GET
	@Path("pricestats")
	public JsonValue priceStats(@QueryParam String q) {
		AggregationKey<JsonObject> priceStats = AggregationKey.of( "price-stats" );

		SearchResult<TShirt> result = searchSession.search( TShirt.class )
				.extension( ElasticsearchExtension.get() )
				.where( f -> {
					if ( q == null || q.isBlank() ) {
						return f.matchAll();
					}
					else {
						return f.simpleQueryString()
								.fields( "name", "collection.keywords",
										"variants.color", "variants.size"
								)
								.matching( q );
					}
				} )
				.aggregation( priceStats, f -> f.fromJson( "{\n" +
						"  \"stats\": {\n" +
						"    \"field\": \"variants.price\"\n" +
						"  }\n" +
						"}" ) )
				.fetch( 0 );

		return JsonUtil.toJson( result.aggregation( priceStats ).toString() );
	}

	@GET
	@Path("suggest")
	public JsonValue suggest(@QueryParam String terms) {
		ElasticsearchSearchResult<TShirt> result = searchSession.search( TShirt.class )
				.extension( ElasticsearchExtension.get() )
				.where( f -> f.matchAll() )
				.requestTransformer( context -> {
					JsonObject body = context.body();
					body.add( "suggest", jsonObject( suggest -> {
						suggest.addProperty( "text", terms );
						suggest.add( "name-suggest-phrase", gson.fromJson(
								"{\n" +
										"  \"phrase\": {\n" +
										"    \"field\": \"name_suggest\",\n" +
										"    \"size\": 2,\n" +
										"    \"gram_size\": 3,\n" +
										"    \"direct_generator\": [ {\n" +
										"      \"field\": \"name_suggest\",\n" +
										"      \"suggest_mode\": \"always\"\n" +
										"    }, {\n" +
										"      \"field\" : \"name_suggest_reverse\",\n" +
										"      \"suggest_mode\" : \"always\",\n" +
										"      \"pre_filter\" : \"suggest_reverse\",\n" +
										"      \"post_filter\" : \"suggest_reverse\"\n" +
										"    } ],\n" +
										"    \"highlight\": {\n" +
										"      \"pre_tag\": \"<em>\",\n" +
										"      \"post_tag\": \"</em>\"\n" +
										"    }\n" +
										"  }\n" +
										"}",
								JsonObject.class
						) );
					} ) );
				} )
				.fetch( 0 );

		JsonObject responseBody = result.responseBody();
		JsonArray mySuggestResults = responseBody.getAsJsonObject( "suggest" )
				.getAsJsonArray( "name-suggest-phrase" );
		return JsonUtil.toJson( mySuggestResults.toString() );
	}

	private TShirt find(long id) {
		TShirt entity = TShirt.findById( id );
		if ( entity == null ) {
			throw new NotFoundException();
		}
		return entity;
	}

	private static JsonObject jsonObject(Consumer<JsonObject> instructions) {
		JsonObject object = new JsonObject();
		instructions.accept( object );
		return object;
	}

	private static JsonArray jsonArray(JsonElement... elements) {
		JsonArray array = new JsonArray();
		for ( JsonElement element : elements ) {
			array.add( element );
		}
		return array;
	}
}