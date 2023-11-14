package org.hibernate.demos.hsearchfeatureexamples.dto;

import org.hibernate.demos.hsearchfeatureexamples.model.FashionSeason;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "year", "season" })
public record FashionCollectionOutputDto(
		long id,
		Integer year,
		FashionSeason season,
		String keywords
) {
}
