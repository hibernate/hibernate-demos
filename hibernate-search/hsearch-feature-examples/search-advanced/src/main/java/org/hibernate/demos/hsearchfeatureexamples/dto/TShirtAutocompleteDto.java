package org.hibernate.demos.hsearchfeatureexamples.dto;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IdProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "name" })
@ProjectionConstructor
public record TShirtAutocompleteDto(
		@IdProjection
		long id,
		String name
) {

}
