/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.repo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import javax.ejb.EJBException;
import javax.inject.Inject;

import org.hibernate.demo.message.account.core.entity.User;
import org.hibernate.demo.message.account.core.service.UserService;
import org.hibernate.demo.message.account.repo.service.util.DeploymentUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Andrea Boriero
 */
@RunWith(Arquillian.class)
public class UserServiceIT {
	@Deployment
	public static WebArchive create() {
		return DeploymentUtil.create();
	}

	@Inject
	private UserService userService;

	@Test(expected = EJBException.class)
	public void testFindUser() {
		userService.findByUsername( "NON_EXISTING_USER" );
	}

	@Test
	public void testCreateUser() {
		User user = new User( "fabio" );
		User createdUser = userService.createNewUser( user );
		assertThat( createdUser.getId(), notNullValue() );
	}

	@Test(expected = EJBException.class)
	public void testTryToCreateSameUseTwoTimes() {
		userService.createNewUser( new User( "sanne" ) );
		userService.createNewUser( new User( "sanne" ) );
	}
}
