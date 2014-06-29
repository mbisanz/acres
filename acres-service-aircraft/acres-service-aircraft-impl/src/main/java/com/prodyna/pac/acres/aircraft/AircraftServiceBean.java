package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Monitored;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

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
		return em.createQuery("select ac from Aircraft ac", Aircraft.class)
				.getResultList();
	}

	@Override
	public Aircraft createAircraft(Aircraft aircraft) {
		em.persist(aircraft);
		return aircraft;
	}

	@Override
	public Aircraft updateAircraft(Aircraft aircraft) {
		Aircraft existing = em.find(Aircraft.class, aircraft.getId());
		if (existing == null) {
			throw new NoResultException("Aircraft does not exist");
		}
		return em.merge(aircraft);
	}

	@Override
	public void deleteAircraft(long id) {
		Aircraft existing = em.find(Aircraft.class, id);
		if (existing == null) {
			throw new NoResultException("Aircraft does not exist");
		}
		em.remove(existing);
	}

	@Override
	public Aircraft findAircraft(String registration) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Aircraft> criteria = cb.createQuery(Aircraft.class);
		Root<Aircraft> room = criteria.from(Aircraft.class);
		CriteriaQuery<Aircraft> query = criteria.select(room);
		if (registration != null) {
			query.where(cb.equal(room.get("registration"), registration));
		}
		Aircraft result = em.createQuery(criteria).getSingleResult();
		return result;
	}
}
