/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.post.core.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.demo.message.post.core.entity.Message;

import org.slf4j.Logger;

/**
 * @author Fabio Massimo Ercoli
 */
@ApplicationScoped
public class MessageRepo {

	@Inject
	private EntityManager em;

	public MessageRepo() {
	}

	public MessageRepo(EntityManager em) {
		this.em = em;
	}

	public void add(@Valid Message post) {
		em.persist(post);
	}

	public Message findById( Long id ) {
		return em.find( Message.class, id );
	}

	public void remove(Message message) {
		em.remove( message );
	}
}
