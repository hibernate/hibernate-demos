package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

public record TShirtInputDto(
		String name,
		List<TShirtVariantDto> variants,
		Long collection
) {
}
