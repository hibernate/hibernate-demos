/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.core.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple resource producer for default {@link EntityManager} and slf4j {@link Logger}.
 *
 * @author Fabio Massimo Ercoli
 */
@ApplicationScoped
public class ResourcesProducer {

	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	private Logger produceLog(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger( injectionPoint.getMember().getDeclaringClass().getName());
	}

}
