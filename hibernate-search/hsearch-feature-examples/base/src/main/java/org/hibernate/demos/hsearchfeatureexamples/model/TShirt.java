package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

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
