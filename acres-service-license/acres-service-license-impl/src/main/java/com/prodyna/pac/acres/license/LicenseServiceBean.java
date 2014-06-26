package com.prodyna.pac.acres.license;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import com.prodyna.pac.acres.aircraft.AircraftType;
import com.prodyna.pac.acres.aircraft.AircraftTypeService;
import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Unsecured
@Stateless
@Logged
@Monitored
public class LicenseServiceBean implements LicenseService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

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

	@Override
	public List<License> findLicenses(String login, String acType) {
		User user = null;
		if (login != null) {
			user = userService.findUser(login);
		}
		AircraftType aircraftType = null;
		if (acType != null) {
			aircraftType = aircraftTypeService.findAircraftType(acType);
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<License> cq = cb.createQuery(License.class);
		Root<License> license = cq.from(License.class);
		List<Predicate> predicates = new ArrayList<>();
		if (user != null) {
			predicates.add(cb.equal(license.get("user"), user));
		}
		if (aircraftType != null) {
			predicates.add(cb.equal(license.get("aircraftType"), aircraftType));
		}
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		List<License> result = em.createQuery(cq).getResultList();
		return result;
	}
}
