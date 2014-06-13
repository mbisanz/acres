package com.prodyna.pac.acres.aircraft;

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

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;

@Unsecured
@Stateless
@Logged
@Monitored
public class AircraftServiceBean implements AircraftService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Override
	public Aircraft readAircraft(long id) {
		return em.find(Aircraft.class, id);
	}

	@Override
	public List<Aircraft> readAllAircrafts() {
		return em.createQuery("select ac from Aircraft ac", Aircraft.class).getResultList();
	}

	@Override
	public Aircraft createAircraft(Aircraft aircraft) {
		em.persist(aircraft);
		return aircraft;
	}

	@Override
	public Aircraft updateAircraft(Aircraft aircraft) {
		return em.merge(aircraft);
	}

	@Override
	public void deleteAircraft(long id) {
		em.remove(em.find(Aircraft.class, id));
	}

	@Override
	public List<Aircraft> findAircrafts(String registration) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Aircraft> criteria = cb.createQuery(Aircraft.class);
		Root<Aircraft> room = criteria.from(Aircraft.class);
		CriteriaQuery<Aircraft> query = criteria.select(room);

		List<Predicate> predicates = new ArrayList<>();
		if (registration != null) {
			predicates.add(cb.equal(room.get("registration"), registration));
		}
		query = query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		List<Aircraft> result = em.createQuery(criteria).getResultList();
		return result;
	}
}
