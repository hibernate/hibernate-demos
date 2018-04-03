/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.core.repo;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;

import org.hibernate.demo.message.account.core.entity.User;

/**
 * @author Andrea Boriero
 */
@ApplicationScoped
public class UserRepo {

	@Inject
	private EntityManager em;

	public UserRepo() {
	}

	public UserRepo(EntityManager em) {
		this.em = em;
	}

	public void add(@Valid User user) {
		em.persist( user );
	}

	public User findByUserName(String userName) {
		Query query = em.createQuery( "from User u where u.userName = :userName" );
		query.setParameter( "userName", userName );
		return (User) query.getSingleResult();
	}

	public void delete(String userName) {
		Query query = em.createQuery( "delete from User u where u.userName = :userName" );
		query.setParameter( "userName", userName );
		query.executeUpdate();
	}

	public void deleteAll() {
		Query query = em.createQuery( "delete from User" );
		query.executeUpdate();
	}

	public List<User> findAll() {
		return em.createQuery( "from User u order by u.id" ).getResultList();
	}
}
