package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.validation.Valid;

public interface AircraftService {

	List<Aircraft> readAllAircrafts();

	Aircraft readAircraft(long id);

	Aircraft createAircraft(@Valid Aircraft aircraft);

	Aircraft updateAircraft(@Valid Aircraft aircraft);

	void deleteAircraft(long id);

	Aircraft findAircraft(String registration);
}
