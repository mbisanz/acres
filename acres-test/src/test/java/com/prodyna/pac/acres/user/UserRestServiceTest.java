package com.prodyna.pac.acres.user;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresRestTest;
import com.prodyna.pac.acres.showcase.ShowcaseRestService;

@RunWith(Arquillian.class)
public class UserRestServiceTest extends AbstractAcresRestTest {

	private UserService userService;

	@Before
	public void setup() throws Exception {
		userService = createService(UserRestService.class, "admin", "admin");
		createService(ShowcaseRestService.class, "admin", "admin").createShowcase();
	}

	@Test(expected = BadRequestException.class)
	@RunAsClient
	public void testCreateUserNoLogin() throws Exception {
		User user = new User();
		userService.createUser(user);
	}

	@Test(expected = BadRequestException.class)
	@RunAsClient
	public void testCreateUserNoPassword() throws Exception {
		User user = new User();
		user.setLogin("test");
		userService.createUser(user);
	}

	@Test
	@RunAsClient
	public void testCreateUser() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = new User();
		user.setLogin("test");
		user.setPassword("test");
		user = userService.createUser(user);
		Assert.assertNotNull(user.getId());
		Assert.assertNull(user.getPassword());

		List<User> result = userService.readAllUsers();
		Assert.assertEquals(5, result.size());
	}

	@Test(expected = NotAuthorizedException.class)
	@RunAsClient
	public void testCreateUserNotAuthorized() throws Exception {
		userService = createService(UserRestService.class, null, null);

		User user = new User();
		user.setLogin("test");
		user.setPassword("test");
		user = userService.createUser(user);
	}

	@Test
	@RunAsClient
	public void testCreateUserForbidden() throws Exception {
		userService = createService(UserRestService.class, "pilot1", "pilot1");

		User user = new User();
		user.setLogin("test");
		user.setPassword("test");
		try {
			user = userService.createUser(user);
			Assert.fail("ClientErrorException expected");
		} catch (ClientErrorException e) {
			Assert.assertEquals(403, e.getResponse().getStatus());
		}
	}

	@Test
	@RunAsClient
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

	@Test(expected = BadRequestException.class)
	@RunAsClient
	public void testUpdateUserIdChange() throws Exception {
		List<User> before = userService.readAllUsers();
		Assert.assertEquals(4, before.size());

		User user = userService.findUser("pilot2");
		user.setId(4711L);
		userService.updateUser(user);
	}
}
