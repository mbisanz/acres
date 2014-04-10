package com.prodyna.pac.acres.user;

import java.util.List;

public interface UserService {

	public List<User> readAllUsers();

	public User readUser(long id);

	public User createUser(User user);

	public User updateUser(User user);

	public void deleteUser(long id);
}
