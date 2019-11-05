package org.hibernate.demos.quarkus.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class BusinessManager extends PanacheEntity {

	@OneToMany(mappedBy = "assignedManager")
	public List<Client> assignedClients = new ArrayList<>();

	@FullTextField(analyzer = "english")
	public String name;

	public String email;

	public String phone;

}
