/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.demos.wikipedia.data.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

import org.springframework.stereotype.Component;

@Component("analysisConfigurerSpringBean")
public class HsearchElasticsearchWikipediaAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer( "cleaned_text" ).custom()
				.tokenizer( "whitespace" )
				.charFilters( "html_strip" )
				.tokenFilters( "asciifolding", "lowercase", "stop", "porter_stem" );

		context.normalizer( "cleaned_keyword" ).custom()
				.tokenFilters( "asciifolding", "lowercase" );
	}
}
