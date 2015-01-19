/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.GenericGenerator;

/**
 * A hike.
 *
 * @author Gunnar Morling
 */
@Entity
public class Hike {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String description;
	private Date date;
	private BigDecimal difficulty;

	@ManyToOne
	private Person organizer;

	@ElementCollection
	@OrderColumn(name = "sectionNo")
	private List<HikeSection> sections;

	// constructors, getters and setters...

	Hike() {
	}

	public Hike(String description, Date date, BigDecimal difficulty, HikeSection... sections) {
		this.description = description;
		this.date = date;
		this.difficulty = difficulty;
		this.sections = sections != null ? new ArrayList<>( Arrays.<HikeSection>asList( sections ) ) : new ArrayList<HikeSection>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
	}

	public Person getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Person organizer) {
		this.organizer = organizer;
	}

	public List<HikeSection> getSections() {
		return sections;
	}

	public void setSections(List<HikeSection> sections) {
		this.sections = sections;
	}
}
