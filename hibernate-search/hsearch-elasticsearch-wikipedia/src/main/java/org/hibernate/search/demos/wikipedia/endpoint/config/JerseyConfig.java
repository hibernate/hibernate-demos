package org.hibernate.search.demos.wikipedia.endpoint.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.search.demos.wikipedia.endpoint.AdminEndpoint;
import org.hibernate.search.demos.wikipedia.endpoint.PageEndpoint;
import org.hibernate.search.demos.wikipedia.endpoint.error.ToJsonExceptionMapper;

import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register( ToJsonExceptionMapper.class );

		registerEndpoints();
	}

	private void registerEndpoints() {
		register( PageEndpoint.class );
		register( AdminEndpoint.class );
	}
}
