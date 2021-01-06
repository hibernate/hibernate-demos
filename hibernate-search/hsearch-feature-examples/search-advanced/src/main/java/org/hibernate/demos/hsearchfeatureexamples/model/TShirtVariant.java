package org.hibernate.demos.hsearchfeatureexamples.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ScaledNumberField;

@Embeddable
public class TShirtVariant {

	@FullTextField
	@KeywordField(name = "color_keyword", aggregable = Aggregable.YES)
	public String color;

	@FullTextField
	@KeywordField(name = "size_keyword", aggregable = Aggregable.YES)
	@Enumerated(EnumType.STRING)
	public TShirtSize size;

	@Column(precision = 12, scale = 2)
	@ScaledNumberField(aggregable = Aggregable.YES, decimalScale = 2)
	public BigDecimal price;

}
