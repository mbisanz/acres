package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@RequestScoped
@Logged
public class AircraftRestServiceResource implements AircraftRestService {

	@Inject
	@Unsecured
	AircraftService service;

	@Override
	public Aircraft readAircraft(long id) {
		return service.readAircraft(id);
	}

	@Override
	public List<Aircraft> readAllAircrafts() {
		return service.readAllAircrafts();
	}

	@Override
	public Aircraft createAircraft(Aircraft aircraft) {
		return service.createAircraft(aircraft);
	}

	@Override
	public Aircraft updateAircraft(Aircraft aircraft) {
		return service.updateAircraft(aircraft);
	}

	@Override
	public void deleteAircraft(long id) {
		service.deleteAircraft(id);
	}

	@Override
	public Aircraft findAircraft(String registration) {
		return service.findAircraft(registration);
	}
}
