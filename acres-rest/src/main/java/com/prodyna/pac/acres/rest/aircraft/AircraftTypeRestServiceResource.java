package com.prodyna.pac.acres.rest.aircraft;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.acres.aircraft.AircraftType;
import com.prodyna.pac.acres.aircraft.AircraftTypeService;
import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@Stateless
@Logged
public class AircraftTypeRestServiceResource implements AircraftTypeRestService {

	@Inject
	@Unsecured
	AircraftTypeService service;

	@PermitAll
	@Override
	public AircraftType readAircraftType(long id) {
		return service.readAircraftType(id);
	}

	@PermitAll
	@Override
	public List<AircraftType> readAllAircraftTypes() {
		return service.readAllAircraftTypes();
	}

	@RolesAllowed("admin")
	@Override
	public AircraftType createAircraftType(AircraftType aircraftType) {
		return service.createAircraftType(aircraftType);
	}

	@RolesAllowed("admin")
	@Override
	public AircraftType updateAircraftType(AircraftType aircraftType) {
		return service.updateAircraftType(aircraftType);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteAircraftType(long id) {
		service.deleteAircraftType(id);
	}

	@PermitAll
	@Override
	public List<AircraftType> findAircraftTypes(String iataCode, String icaoCode) {
		return service.findAircraftTypes(iataCode, icaoCode);
	}
}
