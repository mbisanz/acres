package com.prodyna.pac.acres.user;

import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.common.exception.AcresValidationException;
import com.prodyna.pac.acres.common.exception.NotFoundException;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.password.SecureHashService;

@RunWith(Arquillian.class)
public class UserServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	private SecureHashService secureHashService;

	@Test(expected = EJBTransactionRolledbackException.class)
	public void testCreateUserNoLogin() throws Exception {
		User user = new User();
		user.setPassword("test");
		userService.createUser(user);
	}

	@Test(expected = AcresValidationException.class)
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
		Assert.assertEquals(secureHashService.calculateHash("test"), user.getPasswordHash());

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

	@Test(expected = NotFoundException.class)
	public void testUpdateUserIdChange() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = userService.findUser("pilot2");
		user.setId(4711L);
		userService.updateUser(user);
	}
}
