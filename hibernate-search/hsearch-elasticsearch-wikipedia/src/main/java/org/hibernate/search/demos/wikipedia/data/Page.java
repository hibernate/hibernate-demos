package org.hibernate.search.demos.wikipedia.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

@Entity
@Indexed(index = "page")
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pageId")
	@SequenceGenerator(name = "pageId", sequenceName = "page_id_seq", allocationSize = 1)
	private Long id;

	@Basic(optional = false)
	@FullTextField(analyzer = "cleaned_text")
	@KeywordField(name = "title_sort", normalizer = "cleaned_keyword", sortable = Sortable.YES)
	private String title;

	@Basic(optional = false)
	@Type(type = "text")
	@FullTextField(analyzer = "cleaned_text")
	private String content;

	@ManyToOne
	@IndexedEmbedded(includePaths = "username")
	// Each user may have contributed to a lot of pages - do not reindex pages automatically when a user changes his info
	// This will work fine because the info we're relying on here is effectively immutable
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.NO)
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
