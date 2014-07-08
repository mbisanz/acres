package com.prodyna.pac.acres.user.context;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.qualifier.Current;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Alternative
@Dependent
public class TestUserProducer {

	@Inject
	private CurrentUserProducerBean currentUserProducer;

	@Inject
	@Unsecured
	private UserService service;

	@Inject
	private LoginConfiguration testUser;

	@Produces
	@Current
	public User getTestUser() {
		String login = testUser.getLogin();
		if (login != null) {
			return service.findUser(login);
		}
		return currentUserProducer.getCurrentUser();
	}
}
