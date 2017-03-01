package org.hibernate.search.demos.wikipedia.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

@Entity
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pageId")
	@SequenceGenerator(name = "pageId", sequenceName = "page_id_seq")
	private Long id;

	@Basic(optional = false)
	private String title;

	@Basic(optional = false)
	@Type(type = "text")
	private String content;

	@ManyToOne
	private User lastContributor;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getLastContributor() {
		return lastContributor;
	}

	public void setLastContributor(User lastContributor) {
		this.lastContributor = lastContributor;
	}

}
