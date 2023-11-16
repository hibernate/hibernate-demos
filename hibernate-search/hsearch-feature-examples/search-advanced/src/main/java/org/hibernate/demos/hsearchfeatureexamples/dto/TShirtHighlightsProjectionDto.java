package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

import org.hibernate.demos.hsearchfeatureexamples.model.TShirtSize;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.HighlightProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "name", "variants" })
public record TShirtHighlightsProjectionDto(
		long id,
		String name,
		List<Variant> variants
) {

	@ProjectionConstructor
	public TShirtHighlightsProjectionDto(
			@IdProjection
			long id,
			@HighlightProjection(path = "name")
			List<String> nameHighlights,
			List<Variant> variants
	) {
		this( id, nameHighlights.get( 0 ), // We only expect one fragment
				variants );
	}

	@ProjectionConstructor
	public record Variant(
			String color,
			TShirtSize size
	) {
	}

}
