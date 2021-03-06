package com.prodyna.pac.acres.user;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.qualifier.Current;
import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

@RequestScoped
@Logged
public class UserRestServiceResource implements UserRestService {

	@Inject
	@Unsecured
	private UserService service;

	@Inject
	@Current
	private Instance<User> user;

	@Override
	public List<User> readAllUsers() {
		return service.readAllUsers();
	}

	@Override
	public User readUser(long id) {
		return service.readUser(id);
	}

	@Override
	public User createUser(User user) {
		return service.createUser(user);
	}

	@Override
	public User updateUser(User user) {
		return service.updateUser(user);
	}

	@Override
	public void deleteUser(long id) {
		service.deleteUser(id);
	}

	@Override
	public User findUser(String login) {
		return service.findUser(login);
	}

	@Override
	public User getCurrentUser() {
		return user.get();
	}
}