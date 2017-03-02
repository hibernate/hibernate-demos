package org.hibernate.search.demos.wikipedia.data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Table(name = "user_") // "user" is an SQL keyword
@Indexed(index = "user")
@AnalyzerDef(
		name = "cleaned_keyword",
		tokenizer = @TokenizerDef(
				factory = KeywordTokenizerFactory.class
		),
		filters = {
				@TokenFilterDef(
						factory = ASCIIFoldingFilterFactory.class
				),
				@TokenFilterDef(
						factory = LowerCaseFilterFactory.class
				)
		}
)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId")
	@SequenceGenerator(name = "userId", sequenceName = "user_id_seq")
	private Long id;

	@Basic(optional = false)
	@Field(analyzer = @Analyzer(definition = "cleaned_keyword"))
	private String username;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
