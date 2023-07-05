package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.math.BigDecimal;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

import org.hibernate.demos.hsearchfeatureexamples.model.TShirtSize;

import lombok.Data;

@Data
@JsonbPropertyOrder({ "color", "size", "price" })
public class TShirtVariantDto {

	String color;
	TShirtSize size;
	BigDecimal price;

}
