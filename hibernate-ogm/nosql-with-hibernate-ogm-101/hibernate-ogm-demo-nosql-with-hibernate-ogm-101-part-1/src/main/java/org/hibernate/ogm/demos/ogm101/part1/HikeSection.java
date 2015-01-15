/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part1;

import javax.persistence.Embeddable;

/**
 * One section of a hike.
 *
 * @author Gunnar Morling
 */
@Embeddable
public class HikeSection {

	private String start;
	private String end;

	// constructors, getters and setters...

	HikeSection() {
	}

	public HikeSection(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}
