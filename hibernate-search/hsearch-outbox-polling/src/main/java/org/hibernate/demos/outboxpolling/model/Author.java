package org.hibernate.demos.outboxpolling.model;

import java.util.List;

import org.hibernate.demos.outboxpolling.config.CustomAnalysisDefinitions;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Author extends PanacheEntity {
	@FullTextField(analyzer = CustomAnalysisDefinitions.NAME)
	public String firstName;

	@FullTextField(analyzer = CustomAnalysisDefinitions.NAME)
	public String lastName;

	@ManyToMany(mappedBy = "authors")
	public List<Book> books;

	@ApplicationScoped
	public static class Repository implements PanacheRepository<Author> {
	}
}
