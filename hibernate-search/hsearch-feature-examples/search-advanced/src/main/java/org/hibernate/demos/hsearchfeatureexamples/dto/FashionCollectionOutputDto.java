package org.hibernate.demos.hsearchfeatureexamples.dto;

import javax.json.bind.annotation.JsonbPropertyOrder;

import org.hibernate.demos.hsearchfeatureexamples.model.FashionSeason;

import lombok.Value;

@Value
@JsonbPropertyOrder({ "id", "year", "season" })
public class FashionCollectionOutputDto {

	long id;
	Integer year;
	FashionSeason season;
	String keywords;

}
