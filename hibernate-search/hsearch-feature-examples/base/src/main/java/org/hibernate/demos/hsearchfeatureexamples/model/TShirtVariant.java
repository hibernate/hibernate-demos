package org.hibernate.demos.hsearchfeatureexamples.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TShirtVariant {

	public String color;

	@Enumerated(EnumType.STRING)
	public TShirtSize size;

	@Column(precision = 12, scale = 2)
	public BigDecimal price;

}
