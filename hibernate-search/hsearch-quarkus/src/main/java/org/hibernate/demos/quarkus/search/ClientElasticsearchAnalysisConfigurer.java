package org.hibernate.demos.quarkus.search;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class ClientElasticsearchAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer( "english" ).custom()
				.tokenizer( "standard" )
				.tokenFilters( "lowercase", "stemmer_english", "asciifolding" );
		context.tokenFilter( "stemmer_english" )
				.type( "stemmer" )
				.param( "language", "english" );
	}
}
