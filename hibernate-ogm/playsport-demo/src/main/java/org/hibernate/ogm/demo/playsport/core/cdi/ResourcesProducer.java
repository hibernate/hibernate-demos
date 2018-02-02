package org.hibernate.ogm.demo.playsport.core.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple resource producer for default {@link EntityManager} and slf4j {@link Logger}.
 */
@ApplicationScoped
public class ResourcesProducer {

    @Produces
    @PersistenceContext
    private EntityManager em;

    @Produces
    private Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
