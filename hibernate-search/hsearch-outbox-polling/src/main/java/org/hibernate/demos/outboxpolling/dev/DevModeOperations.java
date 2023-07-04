package org.hibernate.demos.outboxpolling.dev;

import java.util.concurrent.CompletionStage;

import org.hibernate.demos.outboxpolling.management.AdminEndpoint;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
@IfBuildProfile("dev")
public class DevModeOperations {
	@Inject
	AdminEndpoint adminEndpoint;

	CompletionStage<?> onStart(@Observes StartupEvent ev) {
		return adminEndpoint.reindex();
	}

}
