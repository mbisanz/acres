package com.prodyna.pac.acres.user;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.prodyna.pac.acres.common.security.Unsecured;

@RequestScoped
public class UserRestServiceResource implements UserRestService {

	@Inject
	@Unsecured
	private UserService service;

	@Context
	SecurityContext securityContext;

	@RolesAllowed("admin")
	@Override
	public List<User> readAllUsers() {
		return service.readAllUsers();
	}

	@RolesAllowed("admin")
	@Override
	public User readUser(long id) {
		return service.readUser(id);
	}

	@RolesAllowed("admin")
	@Override
	public User createUser(User user) {
		return service.createUser(user);
	}

	@RolesAllowed("admin")
	@Override
	public User updateUser(User user) {
		return service.updateUser(user);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteUser(long id) {
		service.deleteUser(id);
	}

	@RolesAllowed("admin")
	@Override
	public User findUser(String login) {
		return service.findUser(login);
	}

	@PermitAll
	@Override
	public User getCurrentUser() {
		Principal userPrincipal = securityContext.getUserPrincipal();
		if (userPrincipal == null) {
			return null;
		}
		User user = service.findUser(userPrincipal.getName());
		return user;
	}
}