/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.account.core.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.demo.message.account.core.entity.User;
import org.hibernate.demo.message.account.core.repo.UserRepo;

/**
 * @author Andrea Boriero
 */
@Path("/user")
@Stateless
public class UserService {

	@Inject
	private UserRepo repo;

	@GET
	@Path( "all" )
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAllUsers() {
		return repo.findAll();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User findByUsername(@QueryParam("username") String username) {
		return repo.findByUserName( username );
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public User createNewUser(User user) {
		repo.add( user );
		return user;
	}

}
