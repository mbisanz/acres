package com.prodyna.pac.acres.user;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

@Stateless
public class UserServiceBean implements UserService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Override
	@PermitAll
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@Override
	@PermitAll
	public User readUser(long id) {
		return em.find(User.class, id);
	}

	@Override
	@PermitAll
	public List<User> readAllUsers() {
		return em.createQuery("select u from User u", User.class)
				.getResultList();
	}

	@Override
	@RolesAllowed({ "acres_user", "acres_admin" })
	public User updateUser(User user) {
		return em.merge(user);
	}

	@Override
	@RolesAllowed({ "acres_admin" })
	public void deleteUser(long id) {
		em.remove(em.find(User.class, id));
	}
}
