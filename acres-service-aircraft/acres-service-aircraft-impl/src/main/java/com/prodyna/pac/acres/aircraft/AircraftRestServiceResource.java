package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@Stateless
@Logged
public class AircraftRestServiceResource implements AircraftRestService {

	@Inject
	@Unsecured
	AircraftService service;

	@PermitAll
	@Override
	public Aircraft readAircraft(long id) {
		return service.readAircraft(id);
	}

	@PermitAll
	@Override
	public List<Aircraft> readAllAircrafts() {
		return service.readAllAircrafts();
	}

	@RolesAllowed("admin")
	@Override
	public Aircraft createAircraft(Aircraft aircraft) {
		return service.createAircraft(aircraft);
	}

	@RolesAllowed("admin")
	@Override
	public Aircraft updateAircraft(Aircraft aircraft) {
		return service.updateAircraft(aircraft);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteAircraft(long id) {
		service.deleteAircraft(id);
	}

	@PermitAll
	@Override
	public List<Aircraft> findAircrafts(String registration) {
		return service.findAircrafts(registration);
	}
}
