/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos.hswithes.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed(index = "videogame")
public class VideoGame {

	@Id
	@GeneratedValue
	public long id;

	@Field()
	public String title;

	@Field
	public String description;

	@Field
	public int rating;

	@Field(name="release")
	public Date publishingDate;

	@IndexedEmbedded
	public Publisher publisher;

	@ElementCollection
	@Field
	@IndexedEmbedded
	public List<String> tags = new ArrayList<>();

	@ManyToMany
	@IndexedEmbedded
	public List<Character> characters = new ArrayList<>();

	VideoGame() {
	}

	private VideoGame(String title, String description, int rating, Date publishingDate, Publisher publisher, List<Character> characters, List<String> tags) {
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.publishingDate = publishingDate;
		this.publisher = publisher;
		this.characters.addAll( characters );
		this.tags.addAll( tags );
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "VideoGame [id=" + id + ", title=" + title + "]";
	}

	public static class Builder {

		private String title;
		private String description;
		private int rating;
		private Date publishingDate;
		private Publisher publisher;
		private List<String> tags = new ArrayList<>();
		private List<Character> characters = new ArrayList<>();

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withRating(int rating) {
			this.rating = rating;
			return this;
		}

		public Builder withPublishingDate(Date publishingDate) {
			this.publishingDate = publishingDate;
			return this;
		}

		public Builder withPublisher(Publisher publisher) {
			this.publisher = publisher;
			return this;
		}

		public Builder withTags(String... tags) {
			this.tags.addAll( Arrays.asList( tags ) );
			return this;
		}

		public Builder withCharacters(Character... characters) {
			this.characters.addAll( Arrays.asList( characters ) );
			return this;
		}

		public VideoGame build() {
			VideoGame game = new VideoGame( title, description, rating, publishingDate, publisher, characters, tags );

			for ( Character character : game.characters ) {
				character.appearsIn.add( game );
			}

			return game;
		}
	}
}
