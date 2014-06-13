package com.prodyna.pac.acres.aircraft;

import java.util.List;

public interface AircraftTypeService {

	List<AircraftType> readAllAircraftTypes();

	AircraftType readAircraftType(long id);

	AircraftType createAircraftType(AircraftType aircraftType);

	AircraftType updateAircraftType(AircraftType aircraftType);

	void deleteAircraftType(long id);

	List<AircraftType> findAircraftTypes(String iataCode, String icaoCode);
}
