package org.hibernate.demos.quarkus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class Client extends PanacheEntity {

	@FullTextField(analyzer = "english")
	public String name;

	@ManyToOne
	@IndexedEmbedded
	public BusinessManager assignedManager;

}