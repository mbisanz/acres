package com.prodyna.pac.acres.user.context;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.qualifier.Current;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

/**
 * CDI producer for the current user via {@link UserService}.
 * 
 * @author Martin Bisanz, PRODYNA AG
 */
@Stateless
public class CurrentUserProducerBean {

	@Inject
	private Logger log;

	@Inject
	@Unsecured
	private UserService service;

	@Resource
	private SessionContext context;

	@Produces
	@Current
	public User getCurrentUser() {
		if (context == null) {
			return null;
		}
		Principal userPrincipal = context.getCallerPrincipal();
		if (userPrincipal == null) {
			return null;
		}
		try {
			User user = service.findUser(userPrincipal.getName());
			return user;
		} catch (Exception e) {
			log.error("Could not resolve user for user principal [{}]",
					userPrincipal.getName());
			return null;
		}
	}
}
