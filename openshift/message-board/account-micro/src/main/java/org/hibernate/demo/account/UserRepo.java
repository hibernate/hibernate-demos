package org.hibernate.demo.account;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.hibernate.Session;

@ApplicationScoped
public class UserRepo {

	@PersistenceContext
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
		return em.unwrap( Session.class )
			.byNaturalId( User.class ).using( "userName", userName )
			.load();
	}

	public void delete(String userName) {
		User user = findByUserName( userName );
		if (user != null) {
			em.remove( user );
		}
	}

	public List<User> findAll() {
		return em.createQuery( "from User u order by u.id" ).getResultList();
	}
}
