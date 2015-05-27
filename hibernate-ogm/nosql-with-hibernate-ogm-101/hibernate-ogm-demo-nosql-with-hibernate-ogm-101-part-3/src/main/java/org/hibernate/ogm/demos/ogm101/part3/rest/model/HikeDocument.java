/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.model;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.model.HikeSection;

/**
 * The {@link Hike} representation used during REST calls.
 *
 * @author Davide D'Alto
 */
public class HikeDocument {

	private URI organizer;
	private String description;
	private String date;
	private BigDecimal difficulty;
	private List<HikeSection> sections = new ArrayList<>();

	public URI getOrganizer() {
		return organizer;
	}

	public void setOrganizer(URI organizer) {
		this.organizer = organizer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
	}

	public List<HikeSection> getSections() {
		return sections;
	}

	public void setSections(List<HikeSection> sections) {
		this.sections = sections;
	}
}