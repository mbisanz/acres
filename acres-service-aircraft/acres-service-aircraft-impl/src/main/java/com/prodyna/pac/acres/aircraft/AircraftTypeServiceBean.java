package com.prodyna.pac.acres.aircraft;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;

@Stateless
@Logged
public class AircraftTypeServiceBean implements AircraftTypeService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@PermitAll
	@Override
	public AircraftType readAircraftType(long id) {
		return em.find(AircraftType.class, id);
	}

	@PermitAll
	@Override
	public List<AircraftType> readAllAircraftTypes() {
		return em.createQuery("select act from AircraftType act", AircraftType.class).getResultList();
	}

	@RolesAllowed("admin")
	@Override
	public AircraftType createAircraftType(AircraftType aircraftType) {
		em.persist(aircraftType);
		return aircraftType;
	}

	@RolesAllowed("admin")
	@Override
	public AircraftType updateAircraftType(AircraftType aircraftType) {
		return em.merge(aircraftType);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteAircraftType(long id) {
		em.remove(em.find(AircraftType.class, id));
	}

	@PermitAll
	@Override
	public List<AircraftType> findAircraftTypes(String iataCode, String icaoCode) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AircraftType> criteria = cb.createQuery(AircraftType.class);
		Root<AircraftType> room = criteria.from(AircraftType.class);
		CriteriaQuery<AircraftType> query = criteria.select(room);

		List<Predicate> predicates = new ArrayList<>();
		if (iataCode != null) {
			predicates.add(cb.equal(room.get("iataCode"), iataCode));
		}
		if (icaoCode != null) {
			predicates.add(cb.equal(room.get("icaoCode"), icaoCode));
		}
		query = query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		List<AircraftType> result = em.createQuery(criteria).getResultList();
		return result;
	}
}
