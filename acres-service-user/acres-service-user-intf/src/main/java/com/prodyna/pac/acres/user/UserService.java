package com.prodyna.pac.acres.user;

import java.util.List;

public interface UserService {

	List<User> readAllUsers();

	User readUser(long id);

	User createUser(User user);

	User updateUser(User user);

	void deleteUser(long id);

	User findUser(String login);
}
