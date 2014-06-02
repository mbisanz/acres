package com.prodyna.pac.acres.aircraft;

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
}
