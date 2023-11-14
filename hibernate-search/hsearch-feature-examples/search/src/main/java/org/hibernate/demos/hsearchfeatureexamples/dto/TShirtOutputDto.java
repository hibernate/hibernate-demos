package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "name", "variants", "collection" })
public record TShirtOutputDto(
		long id,
		String name,
		List<TShirtVariantDto> variants,
		FashionCollectionOutputDto collection
) {
}
