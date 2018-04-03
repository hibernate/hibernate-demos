/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.repo;

import java.util.List;
import javax.persistence.NoResultException;

import org.hibernate.demo.message.account.core.entity.User;
import org.hibernate.demo.message.account.core.repo.UserRepo;
import org.hibernate.demo.message.test.BaseEntityManagerFunctionalTestCase;

import org.hibernate.testing.transaction.TransactionUtil;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Andrea Boriero
 */
public class UserRepoTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { User.class };
	}

	@Test
	public void createUserTest() {
		String userName = "hibernate";
		User persistedUser = TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			User user = new User( userName );
			repo.add( user );
			return user;
		} );
		assertNotNull( persistedUser.getId() );
	}

	@Test(expected = NoResultException.class)
	public void findNonExistingUserNameTest() {
		String userName = "hibernate";
		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			repo.findByUserName( userName );
		} );
	}

	@Test
	public void findByUserNameTest() {
		String userName = "hibernate";
		User persistedUser = TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			User user = new User( userName );
			repo.add( user );
			return user;
		} );
		assertNotNull( persistedUser.getId() );

		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			User result = repo.findByUserName( userName );
			assertNotNull( result );
		} );
	}

	@Test(expected = NoResultException.class)
	public void deleteUserTest() {
		String userName = "hibernate";
		User persistedUser = TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			User user = new User( userName );
			repo.add( user );
			return user;
		} );
		assertNotNull( persistedUser.getId() );

		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			repo.delete( userName );
		} );

		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			repo.findByUserName( userName );
		} );
	}

	@Test
	public void findAllTest(){
		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			repo.add( new User( "hibernate_orm" ) );
			repo.add( new User("hibernate_ogm") );
		} );

		TransactionUtil.doInJPA( this::entityManagerFactory, entityManager -> {
			UserRepo repo = new UserRepo( entityManager );
			List<User> users = repo.findAll();
			assertThat( users.size(), is(2) );
		} );
	}
}
