package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class FashionCollection extends PanacheEntity {

	public Integer year;

	@Enumerated(EnumType.STRING)
	public FashionSeason season;

	public String keywords;

	@OneToMany(mappedBy = "collection")
	public List<TShirt> content;

}
