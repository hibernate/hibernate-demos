package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

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
