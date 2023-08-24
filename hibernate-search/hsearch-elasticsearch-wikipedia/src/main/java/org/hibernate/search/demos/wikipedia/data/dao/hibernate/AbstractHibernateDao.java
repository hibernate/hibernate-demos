package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

public class AbstractHibernateDao {
	
	@PersistenceContext
	private EntityManager em;
	
	protected final EntityManager getEm() {
		return em;
	}
	
	protected final JPAQuery<?> query() {
		return new JPAQuery<>( em );
	}

}
