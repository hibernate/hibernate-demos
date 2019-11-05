package org.hibernate.demos.quarkus.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class Client extends PanacheEntity {

	@FullTextField(analyzer = "english")
	public String name;

	@ManyToOne
	public BusinessManager assignedManager;

}
