/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Fabio Massimo Ercoli
 */
@Entity
public class Message implements Comparable<Message> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
	@SequenceGenerator(name = "post_gen", sequenceName = "post_seq", initialValue = 1)
	private Long id;

	@NotEmpty
	private String username;

	@NotEmpty
	private String body;

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(CascadeType.PERSIST)
	private Set<Tag> tags = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date moment = new Date();

	private Message() {
	}

	public Message(String username, String body) {
		this.username = username;
		this.body = body;
	}

	public void addTag(String tagName) {
		this.tags.add( new Tag( tagName ) );
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getBody() {
		return body;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", username='" + username + '\'' +
				", body='" + body + '\'' +
				", tags=" + tags +
				", moment=" + moment +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Message message = (Message) o;
		return Objects.equals( username, message.username ) &&
				Objects.equals( moment, message.moment );
	}

	@Override
	public int hashCode() {

		return Objects.hash( username, moment );
	}

	@Override
	public int compareTo(Message o) {
		return this.moment.compareTo( o.moment );
	}
}
