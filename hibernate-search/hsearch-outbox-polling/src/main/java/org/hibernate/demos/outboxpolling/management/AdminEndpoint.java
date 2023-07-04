package org.hibernate.demos.outboxpolling.management;

import java.util.concurrent.CompletionStage;

import org.hibernate.demos.outboxpolling.util.VertxUtils;
import org.hibernate.search.mapper.orm.mapping.SearchMapping;

import io.quarkus.vertx.http.ManagementInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

// Note this endpoint is exposed on the management interface (port 9000 by default)
@ApplicationScoped
public class AdminEndpoint {
	@Inject
	SearchMapping searchMapping;

	public void registerManagementRoutes(@Observes ManagementInterface mi) {
		mi.router().post( "/admin/reindex" )
				.handler( VertxUtils.chunkedHandler( "Mass indexing", this::reindex ) );
	}

	public CompletionStage<?> reindex() {
		return searchMapping.scope( Object.class ).massIndexer().start();
	}
}
