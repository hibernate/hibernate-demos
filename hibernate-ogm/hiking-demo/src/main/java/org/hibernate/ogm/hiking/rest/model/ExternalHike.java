package org.hibernate.ogm.hiking.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Section;

public class ExternalHike {

	private String id;
	private String from;
	private String to;
	private ExternalTrip recommendedTrip;
	private List<Section> sections = new ArrayList<>();

	public ExternalHike() {
	}

	public ExternalHike(Hike hike) {
		this.id = hike.id;
		this.from = hike.start;
		this.to = hike.destination;
		this.recommendedTrip = hike.recommendedTrip != null ? new ExternalTrip( hike.recommendedTrip ) : null;

		for ( Section section : hike.sections ) {
			if ( section != null ) {
				sections.add( section );
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public ExternalTrip getRecommendedTrip() {
		return recommendedTrip;
	}

	public void setRecommendedTrip(ExternalTrip recommendedTrip) {
		this.recommendedTrip = recommendedTrip;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "ExternalHike [id=" + id + ", from=" + from + ", to=" + to + ", recommendedTrip=" + recommendedTrip + ", sections=" + sections + "]";
	}
}
