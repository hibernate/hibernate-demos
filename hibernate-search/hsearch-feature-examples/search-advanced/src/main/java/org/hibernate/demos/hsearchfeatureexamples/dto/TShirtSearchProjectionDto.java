package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

import org.hibernate.demos.hsearchfeatureexamples.model.TShirtSize;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "name", "variants" })
@ProjectionConstructor
public record TShirtSearchProjectionDto(
		@IdProjection
		long id,
		String name,
		List<Variant> variants
) {

	@ProjectionConstructor
	public record Variant(
			String color,
			TShirtSize size
	) {
	}

}
