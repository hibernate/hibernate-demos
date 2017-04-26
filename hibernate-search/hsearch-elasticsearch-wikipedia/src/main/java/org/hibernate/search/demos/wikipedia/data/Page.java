package org.hibernate.search.demos.wikipedia.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Indexed(index = "page")
/*
 * Note: analyzer definitions are globally available,
 * you don't need to repeat them for each single entity. 
 */
@AnalyzerDef(
		name = "cleaned_text",
		charFilters = {
				@CharFilterDef(
						factory = HTMLStripCharFilterFactory.class
				)
		},
		tokenizer = @TokenizerDef(
				factory = WhitespaceTokenizerFactory.class
		),
		filters = {
				@TokenFilterDef(
						factory = ASCIIFoldingFilterFactory.class
				),
				@TokenFilterDef(
						factory = LowerCaseFilterFactory.class
				),
				@TokenFilterDef(
						factory = StopFilterFactory.class
				),
				@TokenFilterDef(
						factory = PorterStemFilterFactory.class
				)
		}
)
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pageId")
	@SequenceGenerator(name = "pageId", sequenceName = "page_id_seq")
	private Long id;

	@Basic(optional = false)
	@Field(analyzer = @Analyzer(definition = "cleaned_text"))
	private String title;

	@Basic(optional = false)
	@Type(type = "text")
	@Field(analyzer = @Analyzer(definition = "cleaned_text"))
	private String content;

	@ManyToOne
	@IndexedEmbedded
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
