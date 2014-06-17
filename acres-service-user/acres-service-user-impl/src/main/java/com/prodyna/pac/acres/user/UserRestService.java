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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRestService extends UserService {

	@GET
	@Override
	List<User> readAllUsers();

	@GET
	@Path("{id:[0-9][0-9]*}")
	@Override
	User readUser(@PathParam("id") long id);

	@POST
	@Override
	User createUser(User user);

	@PUT
	@Override
	User updateUser(User user);

	@DELETE
	@Override
	void deleteUser(@PathParam("id") long id);

	@GET
	@Path("search")
	@Override
	User findUser(@QueryParam("login") String login);
}
