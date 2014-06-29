package com.prodyna.pac.acres.user;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.password.SecureHashService;

@RunWith(Arquillian.class)
public class UserServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	private SecureHashService secureHashService;

	@Test(expected = ValidationException.class)
	public void testCreateUserNoLogin() throws Exception {
		User user = new User();
		userService.createUser(user);
	}

	@Test(expected = ValidationException.class)
	public void testCreateUserNoPassword() throws Exception {
		User user = new User();
		user.setLogin("test");
		userService.createUser(user);
	}

	@Test
	public void testCreateUser() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = new User();
		user.setLogin("test");
		user.setPassword("test");
		userService.createUser(user);
		Assert.assertNotNull(user.getId());
		Assert.assertNull(user.getPassword());
		Assert.assertEquals(secureHashService.calculateHash("test"),
				user.getPasswordHash());

		List<User> result = userService.readAllUsers();
		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testUpdateUser() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = userService.findUser("pilot2");
		String passwordHashBefore = user.getPasswordHash();
		user.setLogin("pilot2test");
		userService.updateUser(user);

		Assert.assertEquals(4, userService.readAllUsers().size());
		User result = userService.findUser("pilot2test");
		Assert.assertNotNull(result);
		Assert.assertNull(result.getPassword());
		Assert.assertEquals(passwordHashBefore, result.getPasswordHash());
	}

	@Test
	public void testUpdateUserPasswdChange() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = userService.findUser("pilot2");
		String passwordHashBefore = user.getPasswordHash();
		user.setPassword("new");
		userService.updateUser(user);

		Assert.assertEquals(4, userService.readAllUsers().size());
		User result = userService.findUser("pilot2");
		Assert.assertNotNull(result);
		Assert.assertNull(result.getPassword());
		Assert.assertNotNull(result.getPasswordHash());
		Assert.assertNotEquals(passwordHashBefore, result.getPasswordHash());
	}

	@Test(expected = NoResultException.class)
	public void testUpdateUserIdChange() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = userService.findUser("pilot2");
		user.setId(4711L);
		userService.updateUser(user);
	}
}
