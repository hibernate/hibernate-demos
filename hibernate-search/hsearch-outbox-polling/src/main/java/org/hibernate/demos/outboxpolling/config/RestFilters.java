package org.hibernate.demos.outboxpolling.config;

import org.jboss.resteasy.reactive.server.ServerResponseFilter;

import jakarta.ws.rs.container.ContainerResponseContext;

public class RestFilters {
	@ServerResponseFilter
	public void exceptionMessage(ContainerResponseContext context, Throwable t) {
		if ( t != null && !context.hasEntity() ) {
			context.setEntity( "{\"message\":\"" + t.getMessage() + "\"}" );
		}
	}
}
