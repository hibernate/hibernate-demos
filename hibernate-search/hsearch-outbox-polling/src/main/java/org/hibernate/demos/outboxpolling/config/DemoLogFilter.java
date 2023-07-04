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
		return true;
	}
}