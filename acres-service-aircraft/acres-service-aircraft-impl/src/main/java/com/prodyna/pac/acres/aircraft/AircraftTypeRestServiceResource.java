package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@RequestScoped
@Logged
public class AircraftTypeRestServiceResource implements AircraftTypeRestService {

	@Inject
	@Unsecured
	AircraftTypeService service;

	@Override
	public AircraftType readAircraftType(long id) {
		return service.readAircraftType(id);
	}

	@Override
	public List<AircraftType> readAllAircraftTypes() {
		return service.readAllAircraftTypes();
	}

	@Override
	public AircraftType createAircraftType(AircraftType aircraftType) {
		return service.createAircraftType(aircraftType);
	}

	@Override
	public AircraftType updateAircraftType(AircraftType aircraftType) {
		return service.updateAircraftType(aircraftType);
	}

	@Override
	public void deleteAircraftType(long id) {
		service.deleteAircraftType(id);
	}

	@Override
	public AircraftType findAircraftType(String iataCode) {
		return service.findAircraftType(iataCode);
	}
}
