package org.hibernate.demos.hsearchfeatureexamples.search;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

@Dependent
@Named("analysis")
public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer( "autocomplete" )
				.custom()
				.tokenizer( "whitespace" )
				.tokenFilters( "lowercase", "asciifolding", "autocomplete_edge_ngram" );

		context.tokenFilter( "autocomplete_edge_ngram" )
				.type( "edge_ngram" )
				.param( "min_gram", 1 )
				.param( "max_gram", 10 );

		context.analyzer( "autocomplete_query" )
				.custom()
				.tokenizer( "whitespace" )
				.tokenFilters( "lowercase", "asciifolding" );
	}
}
