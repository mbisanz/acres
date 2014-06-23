package com.prodyna.pac.acres.user;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.password.SecureHashService;

@RunWith(Arquillian.class)
public class UserServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	private SecureHashService secureHashService;

	@Test(expected = Exception.class)
	public void testCreateUserNoLogin() throws Exception {
		User user = new User();
		userService.createUser(user);
	}

	@Test(expected = Exception.class)
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
}
