package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.validation.Valid;

public interface AircraftTypeService {

	List<AircraftType> readAllAircraftTypes();

	AircraftType readAircraftType(long id);

	AircraftType createAircraftType(@Valid AircraftType aircraftType);

	AircraftType updateAircraftType(@Valid AircraftType aircraftType);

	void deleteAircraftType(long id);

	AircraftType findAircraftType(String iataCode);
}
