package org.hibernate.demos.hsearchfeatureexamples.dto;

import org.hibernate.demos.hsearchfeatureexamples.model.FashionSeason;

import lombok.Data;

@Data
public class FashionCollectionInputDto {

	Integer year;
	FashionSeason season;
	String keywords;

}
