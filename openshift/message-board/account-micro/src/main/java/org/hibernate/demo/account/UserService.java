package org.hibernate.demo.account;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
@RequestScoped
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

	@Inject
	private UserRepo repo;

	@GET
	@Path("all")
	public List<User> findAllUsers() {
		return repo.findAll();
	}

	@GET
	public User findByUsername(@QueryParam("username") String username) {
		return repo.findByUserName( username );
	}

	@POST
	public User createNewUser(User user) {
		repo.add( user );
		return user;
	}

	@DELETE
	@Path("username/{username}")
	public void delete(@PathParam("username") String username) {
		repo.delete( username );
	}

}
