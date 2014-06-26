package com.prodyna.pac.acres.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;

@Unsecured
@Stateless
@Logged
@Monitored
public class UserServiceBean implements UserService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Override
	public User readUser(long id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> readAllUsers() {
		return em.createQuery("select u from User u", User.class).getResultList();
	}

	@Override
	public User createUser(User user) {
		if (user.getPassword() == null || user.getPassword().length() == 0) {
			throw new ValidationException("Password is required");
		}
		em.persist(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		return em.merge(user);
	}

	@Override
	public void deleteUser(long id) {
		em.remove(em.find(User.class, id));
	}

	@Override
	public User findUser(String login) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.equal(user.get("login"), login));
		User result = em.createQuery(cq).getSingleResult();
		return result;
	}
}
