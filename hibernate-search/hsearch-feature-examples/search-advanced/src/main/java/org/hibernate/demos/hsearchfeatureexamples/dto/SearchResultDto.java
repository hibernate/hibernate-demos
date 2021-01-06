package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.json.bind.adapter.JsonbAdapter;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTypeAdapter;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@JsonbPropertyOrder({ "totalHitCount", "hits" })
public class SearchResultDto<T> {

	long totalHitCount;
	List<T> hits;
	@JsonbTypeAdapter(FacetsAdapter.class)
	Map<String, Map<?, Long>> facets;

	public SearchResultDto(long totalHitCount, List<T> hits) {
		this( totalHitCount, hits, null );
	}

	public static class FacetsAdapter
			implements JsonbAdapter<Map<String, Map<?, Long>>, Map<String, Map<String, Long>>> {
		@Override
		public Map<String, Map<String, Long>> adaptToJson(Map<String, Map<?, Long>> obj) {
			return obj.entrySet().stream().collect( toMap(
					Map.Entry::getKey,
					e -> e.getValue().entrySet().stream().collect( toMap(
							e2 -> e2.getKey().toString(),
							Map.Entry::getValue
					) )
			) );
		}

		@Override
		public Map<String, Map<?, Long>> adaptFromJson(Map<String, Map<String, Long>> obj) {
			throw new UnsupportedOperationException();
		}

		private static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper,
				Function<? super T, ? extends U> valueMapper) {
			return Collectors.toMap( keyMapper, valueMapper,
					(a, b) -> {
						throw new IllegalStateException( "Not expecting duplicates" );
					},
					LinkedHashMap::new
			);
		}
	}
}
