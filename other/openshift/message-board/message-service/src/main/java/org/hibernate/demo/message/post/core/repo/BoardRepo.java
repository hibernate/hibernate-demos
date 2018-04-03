package org.hibernate.demo.message.post.core.repo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.hibernate.demo.message.post.core.entity.Board;

@ApplicationScoped
public class BoardRepo {

	@Inject
	private EntityManager em;

	public Board find( String username ) {
		return em.find( Board.class, username );
	}

	public void add( @Valid Board board ) {
		em.persist( board );
	}

	public void update( @Valid Board board ) {
		em.merge( board );
	}

	public void delete( Board board ) {
		em.remove( board );
	}

}
