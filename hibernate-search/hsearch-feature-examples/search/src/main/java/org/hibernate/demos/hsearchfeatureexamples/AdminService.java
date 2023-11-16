package org.hibernate.demos.hsearchfeatureexamples;

import org.hibernate.search.mapper.orm.mapping.SearchMapping;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

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
		switch ( LaunchMode.current() ) {
			case NORMAL -> {
			}
			case DEVELOPMENT, TEST -> reindex();
		}
	}
}