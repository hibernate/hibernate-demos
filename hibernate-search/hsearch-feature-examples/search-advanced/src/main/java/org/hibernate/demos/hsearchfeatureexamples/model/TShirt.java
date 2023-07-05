package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;

import org.hibernate.search.engine.backend.types.ObjectStructure;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class TShirt extends PanacheEntity {

	@FullTextField(analyzer = "english", projectable = Projectable.YES)
	@FullTextField(name = "name_autocomplete", analyzer = "autocomplete", searchAnalyzer = "autocomplete_query")
	@FullTextField(name = "name_suggest", analyzer = "suggest_trigram")
	@FullTextField(name = "name_suggest_reverse", analyzer = "suggest_reverse")
	public String name;

	@ElementCollection
	@OrderColumn
	@IndexedEmbedded
	@IndexedEmbedded(name = "variants_nested", structure = ObjectStructure.NESTED)
	public List<TShirtVariant> variants = new ArrayList<>();

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@IndexedEmbedded
	public FashionCollection collection;

}
