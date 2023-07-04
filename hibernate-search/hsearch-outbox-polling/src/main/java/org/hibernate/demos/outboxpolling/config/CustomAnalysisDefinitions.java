package org.hibernate.demos.outboxpolling.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

import io.quarkus.hibernate.search.orm.elasticsearch.SearchExtension;

@SearchExtension
public class CustomAnalysisDefinitions implements ElasticsearchAnalysisConfigurer {
	public static final String ENGLISH_SORT = "english_sort";
	public static final String NAME = "name";

	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.normalizer( ENGLISH_SORT )
				.custom()
				.charFilters( "english_remove_article" )
				.tokenFilters( "lowercase", "asciifolding" );
		context.charFilter( "english_remove_article" ).type( "pattern_replace" )
				.param( "pattern", "^(a|the)\\s*" )
				.param( "replacement", "" )
				.param( "flags", "CASE_INSENSITIVE" );

		context.analyzer( NAME )
				.custom()
				.tokenizer( "standard" )
				.tokenFilters( "lowercase", "asciifolding" );
	}
}
