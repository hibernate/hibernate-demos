package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
