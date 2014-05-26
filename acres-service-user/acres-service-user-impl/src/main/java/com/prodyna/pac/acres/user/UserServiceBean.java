package com.prodyna.pac.acres.user;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;

@Stateless
@Logged
public class UserServiceBean implements UserService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@PermitAll
	@Override
	public User readUser(long id) {
		return em.find(User.class, id);
	}

	@PermitAll
	@Override
	public List<User> readAllUsers() {
		return em.createQuery("select u from User u", User.class).getResultList();
	}

	@RolesAllowed("admin")
	@Override
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@RolesAllowed("admin")
	@Override
	public User updateUser(User user) {
		return em.merge(user);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteUser(long id) {
		em.remove(em.find(User.class, id));
	}
}
