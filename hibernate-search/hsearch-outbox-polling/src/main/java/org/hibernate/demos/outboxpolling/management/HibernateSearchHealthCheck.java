package org.hibernate.demos.outboxpolling.management;

import org.hibernate.demos.outboxpolling.model.Book;
import org.hibernate.search.mapper.orm.session.SearchSession;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class HibernateSearchHealthCheck implements HealthCheck {
	private static final String NAME = "Hibernate Search health check";

	@Inject
	SearchSession searchSession;

	@Override
	@Transactional
	public HealthCheckResponse call() {
		try {
			searchSession.search( Book.class ).where( f -> f.matchAll() ).fetchTotalHitCount();
			return HealthCheckResponse.up( NAME );
		}
		catch (RuntimeException e) {
			return HealthCheckResponse.down( NAME );
		}
	}
}
