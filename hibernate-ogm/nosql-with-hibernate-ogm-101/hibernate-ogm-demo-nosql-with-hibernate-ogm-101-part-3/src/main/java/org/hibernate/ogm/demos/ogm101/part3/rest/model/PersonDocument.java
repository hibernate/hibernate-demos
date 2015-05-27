/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.model;

import java.net.URI;
import java.util.Set;

import org.hibernate.ogm.demos.ogm101.part3.model.Person;

/**
 * The {@link Person} representation used during REST calls.
 *
 * @author Davide D'Alto
 */
public class PersonDocument {

	private String firstName;
	private String lastName;
	private Set<URI> organizedHikes;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<URI> getOrganizedHikes() {
		return organizedHikes;
	}

	public void setOrganizedHikes(Set<URI> organizedHikes) {
		this.organizedHikes = organizedHikes;
	}
}
