package org.hibernate.demos.outboxpolling.management;

import org.hibernate.demos.outboxpolling.model.Book;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class HibernateOrmHealthCheck implements HealthCheck {
	private static final String NAME = "Hibernate ORM health check";

	@Override
	public HealthCheckResponse call() {
		try {
			Book.count();
			return HealthCheckResponse.up( NAME );
		}
		catch (RuntimeException e) {
			return HealthCheckResponse.down( NAME );
		}
	}
}
