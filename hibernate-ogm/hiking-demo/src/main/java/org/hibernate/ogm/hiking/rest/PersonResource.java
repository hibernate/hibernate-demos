package org.hibernate.ogm.hiking.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.ogm.hiking.model.Person;
import org.hibernate.ogm.hiking.repository.PersonRepository;
import org.hibernate.ogm.hiking.rest.model.ExternalPerson;

@Path("/persons")
@Stateless
public class PersonResource {

	@Inject
	private PersonRepository personRepository;

	public PersonResource() {
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public List<ExternalPerson> getAllPersons() {
		List<Person> persons = personRepository.getAllPersons();
		List<ExternalPerson> externalPersons = new ArrayList<>( persons.size() );

		for ( Person person : persons ) {
			externalPersons.add( new ExternalPerson( person ) );
		}

		return externalPersons;
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public long createPerson(ExternalPerson externalPerson) {
		Person person = new Person( externalPerson.getName() );
		person = personRepository.createPerson( person );
		return person.id;
	}
}
