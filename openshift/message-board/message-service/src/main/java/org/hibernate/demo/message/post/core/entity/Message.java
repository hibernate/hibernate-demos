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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Tag> tags = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date moment = new Date();

	public Message() {
	}

	public Message(String username, String body) {
		this.username = username;
		this.body = body;
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

	public Date getMoment() {
		return moment;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(Tag tag) {
		this.tags.add( tag );
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
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
				Objects.equals( body, message.body ) &&
				Objects.equals( moment, message.moment );
	}

	@Override
	public int hashCode() {
		return Objects.hash( username, body, moment );
	}

	@Override
	public int compareTo(Message o) {
		int compareMoments = this.moment.compareTo( o.moment );
		if ( compareMoments != 0 ) {
			return compareMoments;
		}

		// if two messages have the same time
		// then compare their bodies
		return this.body.compareTo( o.body );
	}
}
