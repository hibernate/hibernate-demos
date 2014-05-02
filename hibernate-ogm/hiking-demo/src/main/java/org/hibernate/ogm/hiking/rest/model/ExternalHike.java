package org.hibernate.ogm.hiking.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Section;

public class ExternalHike {

	private long id;
	private String from;
	private String to;
	private ExternalPerson organizer;
	private List<Section> sections = new ArrayList<>();

	public ExternalHike() {
	}

	public ExternalHike(Hike hike) {
		this.id = hike.id;
		this.from = hike.start;
		this.to = hike.destination;
		this.organizer = hike.organizer != null ? new ExternalPerson( hike.organizer ) : null;

		for ( Section section : hike.sections ) {
			if ( section != null ) {
				sections.add( section );
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public ExternalPerson getOrganizer() {
		return organizer;
	}

	public void setOrganizer(ExternalPerson organizer) {
		this.organizer = organizer;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "ExternalHike [id=" + id + ", from=" + from + ", to=" + to + ", organizer=" + organizer + ", sections=" + sections + "]";
	}
}
