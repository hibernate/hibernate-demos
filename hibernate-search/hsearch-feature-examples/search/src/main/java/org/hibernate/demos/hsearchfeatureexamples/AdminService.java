package org.hibernate.demos.hsearchfeatureexamples;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import org.hibernate.search.mapper.orm.mapping.SearchMapping;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@Path("/admin")
public class AdminService {

	@Inject
	SearchMapping searchMapping;

	@POST
	@Path("/reindex")
	public void reindex() throws InterruptedException {
		searchMapping.scope( Object.class )
				.massIndexer()
				.startAndWait();
	}

	void reindexOnStart(@Observes StartupEvent event) throws InterruptedException {
		String profile = ProfileManager.getActiveProfile();
		if ( "dev".equals( profile ) || "test".equals( profile ) ) {
			reindex();
		}
	}
}