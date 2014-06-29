package com.prodyna.pac.acres.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.password.SecureHashService;

@Unsecured
@Stateless
@Logged
@Monitored
public class UserServiceBean implements UserService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private SecureHashService secureHashService;

	@Override
	public User readUser(long id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> readAllUsers() {
		return em.createQuery("select u from User u", User.class)
				.getResultList();
	}

	@Override
	public User createUser(User user) {
		if (user.getPassword() == null || user.getPassword().length() == 0) {
			throw new ValidationException("Password is required");
		}
		String passwordHash = secureHashService.calculateHash(user
				.getPassword());
		user.setPasswordHash(passwordHash);
		user.setPassword(null);
		em.persist(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		User existing = em.find(User.class, user.getId());
		if (existing == null) {
			throw new NoResultException("User does not exist");
		}
		if (user.getPassword() != null && user.getPassword().length() > 0) {
			String passwordHash = secureHashService.calculateHash(user
					.getPassword());
			user.setPasswordHash(passwordHash);
		} else {
			user.setPasswordHash(existing.getPasswordHash());
		}
		user.setPassword(null);
		return em.merge(user);
	}

	@Override
	public void deleteUser(long id) {
		User existing = em.find(User.class, id);
		if (existing == null) {
			throw new NoResultException("User does not exist");
		}
		em.remove(existing);
	}

	@Override
	public User findUser(String login) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.equal(user.get("login"), login));
		try {
			User result = em.createQuery(cq).getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
}
