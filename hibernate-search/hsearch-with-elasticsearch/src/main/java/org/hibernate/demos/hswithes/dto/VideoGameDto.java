package org.hibernate.demos.hswithes.dto;

import java.util.Date;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FieldProjection;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

public class VideoGameDto {

	private final String title;
	private final String publisherName;
	private final Date release;

	@ProjectionConstructor
	public VideoGameDto(String title, @FieldProjection(path = "publisher.name") String publisherName,
			Date release) {
		this.title = title;
		this.publisherName = publisherName;
		this.release = release;
	}

	public String getTitle() {
		return title;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public Date getRelease() {
		return release;
	}

	@Override
	public String toString() {
		return "VideoGameDto [title=" + title + ", publisherName=" + publisherName + ", release=" + release + "]";
	}
}
