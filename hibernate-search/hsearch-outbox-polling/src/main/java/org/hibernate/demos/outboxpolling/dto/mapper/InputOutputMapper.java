package org.hibernate.demos.outboxpolling.dto.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.demos.outboxpolling.dto.SearchResultDto;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.query.SearchResult;

import jakarta.inject.Inject;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public abstract class InputOutputMapper<E, I, O> {

	@Inject
	FacetMapper facetMapper;

	@Mapping(target = "id", ignore = true)
	public abstract void input(@MappingTarget E target, I source);

	public abstract O output(E source);

	public abstract List<O> output(List<E> source);

	public final SearchResultDto<O> searchOutput(SearchResult<E> result) {
		return new SearchResultDto<>( result.total().hitCount(), output( result.hits() ) );
	}

	public final SearchResultDto<O> searchOutput(SearchResult<E> result,
			List<AggregationKey<? extends Map<?, Long>>> aggregationKeys) {
		Map<String, Map<String, Long>> facets = new LinkedHashMap<>();
		for ( AggregationKey<? extends Map<?, Long>> aggregationKey : aggregationKeys ) {
			facets.put(
					aggregationKey.name(),
					facetMapper.output( result.aggregation( aggregationKey ) )
			);
		}
		return new SearchResultDto<>( result.total().hitCount(), output( result.hits() ), facets );
	}

}
