package com.prodyna.pac.acres.user;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRestService extends UserService {

	@GET
	@RolesAllowed("admin")
	@Override
	List<User> readAllUsers();

	@GET
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	@Override
	User readUser(@PathParam("id") long id);

	@POST
	@RolesAllowed("admin")
	@Override
	User createUser(User user);

	@PUT
	@RolesAllowed("admin")
	@Override
	User updateUser(User user);

	@DELETE
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	@Override
	void deleteUser(@PathParam("id") long id);

	@GET
	@Path("search")
	@RolesAllowed("admin")
	@Override
	User findUser(@QueryParam("login") String login);

	@GET
	@PermitAll
	@Path("my")
	User getCurrentUser();
}
