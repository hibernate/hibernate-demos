package org.hibernate.demos.outboxpolling.config;

import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.jboss.logmanager.formatters.PatternFormatter;

import io.quarkus.logging.LoggingFilter;

@LoggingFilter(name = "demo-filter")
public final class DemoLogFilter implements Filter {
	private static final Formatter LOG_FORMATTER = new PatternFormatter( "%s" );

	@Override
	public boolean isLoggable(LogRecord record) {
		// Allow logging Elasticsearch requests *except* _count,
		// because we perform a count for health checks so that would flood the logs.
		if ( record.getMessage().contains( "Executed Elasticsearch HTTP" ) ) {
			return !LOG_FORMATTER.format( record ).contains( "/_count" );
		}
		// Prevent warnings about Elasticsearch security being disabled;
		// this is just a demo.
		if ( record.getMessage().contains( "Elasticsearch built-in security features are not enabled" ) ) {
			return false;
		}
		// Prevent warnings about <hibernate-mappings/> being deprecated;
		// this is the only solution for dynamic mapping
		// and we're working with the Hibernate ORM team for a better solution
		// in Hibernate Search 7.0 / Hibernate ORM 6.3/6.4.
		if ( record.getMessage().contains( "HHH90000028" ) ) {
			return false;
		}
		return true;
	}
}