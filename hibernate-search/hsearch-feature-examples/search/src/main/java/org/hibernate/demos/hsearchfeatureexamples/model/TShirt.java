package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class TShirt extends PanacheEntity {

	@FullTextField(analyzer = "english")
	public String name;

	@ElementCollection
	@OrderColumn
	@IndexedEmbedded
	public List<TShirtVariant> variants = new ArrayList<>();

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@IndexedEmbedded
	public FashionCollection collection;

}
