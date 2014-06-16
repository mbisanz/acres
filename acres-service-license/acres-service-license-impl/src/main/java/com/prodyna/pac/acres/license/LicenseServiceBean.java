package com.prodyna.pac.acres.license;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;

@Unsecured
@Stateless
@Logged
@Monitored
public class LicenseServiceBean implements LicenseService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Override
	public License readLicense(long id) {
		return em.find(License.class, id);
	}

	@Override
	public List<License> readAllLicenses() {
		return em.createQuery("select e from License e", License.class).getResultList();
	}

	@Override
	public License createLicense(License License) {
		em.persist(License);
		return License;
	}

	@Override
	public License updateLicense(License License) {
		return em.merge(License);
	}

	@Override
	public void deleteLicense(long id) {
		em.remove(em.find(License.class, id));
	}
}
