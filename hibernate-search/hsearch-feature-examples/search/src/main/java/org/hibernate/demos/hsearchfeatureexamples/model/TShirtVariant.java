package org.hibernate.demos.hsearchfeatureexamples.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Embeddable
public class TShirtVariant {

	@FullTextField
	public String color;

	@FullTextField
	@Enumerated(EnumType.STRING)
	public TShirtSize size;

	@Column(precision = 12, scale = 2)
	public BigDecimal price;

}
