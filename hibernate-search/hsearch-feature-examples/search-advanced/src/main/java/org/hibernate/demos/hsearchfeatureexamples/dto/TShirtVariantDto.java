package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.math.BigDecimal;

import org.hibernate.demos.hsearchfeatureexamples.model.TShirtSize;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "color", "size", "price" })
public record TShirtVariantDto(
		String color,
		TShirtSize size,
		BigDecimal price
) {
}
