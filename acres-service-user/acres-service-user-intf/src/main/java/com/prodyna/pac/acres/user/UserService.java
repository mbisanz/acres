package com.prodyna.pac.acres.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("user")
@Produces("application/json")
@Consumes("application/json")
public interface UserService {

	@GET
	List<User> readAllUsers();

	@GET
	@Path("{id}")
	User readUser(@PathParam("id") long id);

	@POST
	User createUser(User user);

	@PUT
	User updateUser(User user);

	@DELETE
	@Path("{id}")
	void deleteUser(@PathParam("id") long id);
}
