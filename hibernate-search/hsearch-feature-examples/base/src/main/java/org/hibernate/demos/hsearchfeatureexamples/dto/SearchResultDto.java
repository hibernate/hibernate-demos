package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "totalHitCount", "hits" })
public record SearchResultDto<T>(
		long totalHitCount,
		List<T> hits
) {
}
