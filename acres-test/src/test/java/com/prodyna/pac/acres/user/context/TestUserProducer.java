package com.prodyna.pac.acres.user.context;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Alternative
@Dependent
public class TestUserProducer {

	@Inject
	@Unsecured
	private UserService service;

	@Produces
	@Current
	public User getTestUser() {
		return service.findUser("pilot1");
	}
}
