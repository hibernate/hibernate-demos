package org.hibernate.demo.message.post.core.repo;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.demo.message.post.core.entity.Tag;

@ApplicationScoped
public class TagRepo {

	@Inject
	private EntityManager em;

	public Tag findOrCreate(String value) {
		Tag tag = em.find( Tag.class, value );
		if ( tag != null ) {
			return tag;
		}

		tag = new Tag( value );
		em.persist( tag );
		return tag;
	}
}
