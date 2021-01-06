package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;

import lombok.Value;

@Value
@JsonbPropertyOrder({ "id", "name", "variants", "collection" })
public class TShirtOutputDto {

	public static TShirtOutputDto of(long id, String name) {
		return new TShirtOutputDto(id, name, null, null);
	}

	long id;
	String name;
	List<TShirtVariantDto> variants;
	FashionCollectionOutputDto collection;

}
