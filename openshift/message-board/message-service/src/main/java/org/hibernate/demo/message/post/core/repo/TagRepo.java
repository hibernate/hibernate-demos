package org.hibernate.demo.message.post.core.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.demo.message.post.core.entity.Tag;

@ApplicationScoped
public class TagRepo {

	@Inject
	private EntityManager em;

	public Tag find( String tag ) {
		return em.find( Tag.class, tag );
	}

}
