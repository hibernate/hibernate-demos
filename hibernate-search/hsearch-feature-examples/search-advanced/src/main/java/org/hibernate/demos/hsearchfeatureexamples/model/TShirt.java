package org.hibernate.demos.hsearchfeatureexamples.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

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
