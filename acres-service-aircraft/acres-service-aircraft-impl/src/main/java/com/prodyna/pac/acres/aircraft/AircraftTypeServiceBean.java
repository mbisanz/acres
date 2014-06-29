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

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;

@Unsecured
@Stateless
@Logged
@Monitored
public class AircraftTypeServiceBean implements AircraftTypeService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Override
	public AircraftType readAircraftType(long id) {
		return em.find(AircraftType.class, id);
	}

	@Override
	public List<AircraftType> readAllAircraftTypes() {
		return em.createQuery("select act from AircraftType act",
				AircraftType.class).getResultList();
	}

	@Override
	public AircraftType createAircraftType(AircraftType aircraftType) {
		em.persist(aircraftType);
		return aircraftType;
	}

	@Override
	public AircraftType updateAircraftType(AircraftType aircraftType) {
		AircraftType existing = em.find(AircraftType.class,
				aircraftType.getId());
		if (existing == null) {
			throw new NoResultException("AircraftType does not exist");
		}
		return em.merge(aircraftType);
	}

	@Override
	public void deleteAircraftType(long id) {
		AircraftType existing = em.find(AircraftType.class, id);
		if (existing == null) {
			throw new NoResultException("AircraftType does not exist");
		}
		em.remove(existing);
	}

	@Override
	public AircraftType findAircraftType(String iataCode) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AircraftType> cq = cb.createQuery(AircraftType.class);
		Root<AircraftType> aircraftType = cq.from(AircraftType.class);
		if (iataCode != null) {
			cq.where(cb.equal(aircraftType.get("iataCode"), iataCode));
		}
		AircraftType result = em.createQuery(cq).getSingleResult();
		return result;
	}
}
