package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;

import lombok.Data;

@Data
public class TShirtInputDto {

	String name;
	List<TShirtVariantDto> variants;
	Long collection;

}
