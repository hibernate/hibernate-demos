package org.hibernate.demos.quarkus.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Client extends PanacheEntity {

	public String name;

	@ManyToOne
	public BusinessManager assignedManager;

}
