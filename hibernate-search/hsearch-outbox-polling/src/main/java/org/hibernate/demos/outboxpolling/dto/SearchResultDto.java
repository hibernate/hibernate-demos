package org.hibernate.demos.outboxpolling.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "totalHitCount", "hits" })
public record SearchResultDto<T>(
		long totalHitCount,
		List<T> hits,
		Map<String, Map<String, Long>> facets
) {
	public SearchResultDto(long totalHitCount, List<T> hits) {
		this( totalHitCount, hits, null );
	}
}
