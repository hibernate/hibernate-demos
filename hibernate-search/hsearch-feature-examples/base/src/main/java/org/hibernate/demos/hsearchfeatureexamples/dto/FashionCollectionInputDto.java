package org.hibernate.demos.hsearchfeatureexamples.dto;

import org.hibernate.demos.hsearchfeatureexamples.model.FashionSeason;

public record FashionCollectionInputDto(
		Integer year,
		FashionSeason season,
		String keywords
) {
}
