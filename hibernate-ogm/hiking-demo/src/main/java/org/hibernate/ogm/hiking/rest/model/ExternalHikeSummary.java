package org.hibernate.ogm.hiking.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ogm.hiking.model.Hike;
import org.hibernate.ogm.hiking.model.Section;

public class ExternalHikeSummary {

	private String id;
	private String from;
	private String to;

	public ExternalHikeSummary() {
	}

	public ExternalHikeSummary(Hike hike) {
		this.id = hike.id;
		this.from = hike.start;
		this.to = hike.destination;
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

	@Override
	public String toString() {
		return "ExternalHikeSummary [id=" + id + ", from=" + from + ", to=" + to + "]";
	}
}
