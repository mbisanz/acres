package com.prodyna.pac.acres.user;

import java.util.List;

import javax.validation.Valid;

public interface UserService {

	List<User> readAllUsers();

	User readUser(long id);

	User createUser(@Valid User user);

	User updateUser(@Valid User user);

	void deleteUser(long id);

	User findUser(String login);
}
