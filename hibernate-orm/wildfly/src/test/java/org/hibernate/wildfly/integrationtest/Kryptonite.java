/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.wildfly.integrationtest;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class Kryptonite.
 *
 * @author Gunnar Morling
 */
@Entity
public class Kryptonite {

	/** The id. */
	@Id
	public long id;

	/** The description. */
	public String description;
}
