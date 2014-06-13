package com.prodyna.pac.acres.aircraft;

import java.util.List;

public interface AircraftService {

	List<Aircraft> readAllAircrafts();

	Aircraft readAircraft(long id);

	Aircraft createAircraft(Aircraft aircraft);

	Aircraft updateAircraft(Aircraft aircraft);

	void deleteAircraft(long id);

	List<Aircraft> findAircrafts(String registration);
}
