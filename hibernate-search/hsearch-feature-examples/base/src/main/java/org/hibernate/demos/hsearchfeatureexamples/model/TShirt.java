package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class TShirt extends PanacheEntity {

	public String name;

	@ElementCollection
	@OrderColumn
	public List<TShirtVariant> variants = new ArrayList<>();

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public FashionCollection collection;

}
