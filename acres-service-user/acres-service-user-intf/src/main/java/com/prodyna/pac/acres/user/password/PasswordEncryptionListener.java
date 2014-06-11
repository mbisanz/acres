package com.prodyna.pac.acres.user.password;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.prodyna.pac.acres.user.User;

public class PasswordEncryptionListener {
	@Inject
	private BeanManager beanManager;

	/**
	 * Encrypt password before persisting
	 */
	@PrePersist
	@PreUpdate
	public void encryptPassword(Object entity) {
		if (!(entity instanceof User)) {
			return;
		}

		User user = (User) entity;
		user.setPasswordHash(null);

		String password = user.getPassword();
		if (password != null) {
			String passwordHash = getHashService().calculateHash(password);
			user.setPasswordHash(passwordHash);
		}
		user.setPassword(null);
	}

	/*
	 * Directly injecting CDI beans into JPA entity listeners does not work in
	 * Wildfly 8.x, hence BeanManager is used.
	 * 
	 * See https://issues.jboss.org/browse/WFLY-2387
	 */
	@SuppressWarnings("unchecked")
	private SecureHashService getHashService() {
		Bean<SecureHashService> bean = (Bean<SecureHashService>) beanManager.resolve(beanManager
				.getBeans(SecureHashService.class));
		SecureHashService service = (SecureHashService) beanManager.getReference(bean, SecureHashService.class,
				beanManager.createCreationalContext(bean));
		return service;
	}
}
