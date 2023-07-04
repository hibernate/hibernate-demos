package org.hibernate.demos.outboxpolling.model;

import java.util.List;

import org.hibernate.demos.outboxpolling.config.CustomAnalysisDefinitions;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
@Indexed
public class Book extends PanacheEntity {
	@FullTextField(analyzer = "english")
	@KeywordField(name = "title_sort",
			normalizer = CustomAnalysisDefinitions.ENGLISH_SORT,
			searchable = Searchable.NO, sortable = Sortable.YES)
	public String title;

	@GenericField(aggregable = Aggregable.YES)
	public int pageCount;

	@ElementCollection
	@GenericField(aggregable = Aggregable.YES)
	public List<String> genres;

	@ManyToMany
	@IndexedEmbedded
	public List<Author> authors;

	@ApplicationScoped
	public static class Repository implements PanacheRepository<Book> {
	}
}
