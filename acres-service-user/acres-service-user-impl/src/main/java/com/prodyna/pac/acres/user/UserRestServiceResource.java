package com.prodyna.pac.acres.user;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@RequestScoped
public class UserRestServiceResource implements UserRestService {
	
	@Inject
	@Unsecured
	private UserService service;

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
}