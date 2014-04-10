package com.prodyna.pac.acres.user;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Path("private/user")
@Produces("application/json")
@Consumes("application/json")
public class UserPrivateRestService extends UserPublicRestService {

	@Inject
	private UserService userService;

	@POST
	@PermitAll
	public User createUser(User user) {
		return userService.createUser(user);
	}

	@PUT
	@RolesAllowed("acres_user")
	public User updateUser(User user) {
		return userService.updateUser(user);
	}

	@DELETE
	@RolesAllowed("acres_admin")
	@Path("{id}")
	public void deleteUser(@PathParam("id") long id) {
		userService.deleteUser(id);
	}
}
