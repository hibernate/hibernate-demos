/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos.hswithes.model;

import javax.persistence.Embeddable;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Embeddable
public class Publisher {

	@FullTextField
	public String name;

	@FullTextField
	public String address;

	Publisher() {
	}

	public Publisher(String name, String address) {
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Publisher [name=" + name + ", address=" + address + "]";
	}
}
