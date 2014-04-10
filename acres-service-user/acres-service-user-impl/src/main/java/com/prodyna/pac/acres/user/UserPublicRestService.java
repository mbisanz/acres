package com.prodyna.pac.acres.user;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Path("public/user")
@Produces("application/json")
@Consumes("application/json")
public class UserPublicRestService {

	@Inject
	private UserService userService;

	@GET
	public List<User> readAllUsers() {
		return userService.readAllUsers();
	}

	@GET
	@Path("{id}")
	public User readUser(@PathParam("id") long id) {
		return userService.readUser(id);
	}
}
