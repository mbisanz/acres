package com.prodyna.pac.acres.rest.aircraft;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.prodyna.pac.acres.aircraft.AircraftType;
import com.prodyna.pac.acres.aircraft.AircraftTypeService;

@Path("acType")
@Produces("application/json")
@Consumes("application/json")
public interface AircraftTypeRestService extends AircraftTypeService {

	@GET
	@Override
	List<AircraftType> readAllAircraftTypes();

	@GET
	@Path("{id}")
	@Override
	AircraftType readAircraftType(@PathParam("id") long id);

	@POST
	@Override
	AircraftType createAircraftType(AircraftType aircraftType);

	@PUT
	@Override
	AircraftType updateAircraftType(AircraftType aircraftType);

	@DELETE
	@Path("{id}")
	@Override
	void deleteAircraftType(@PathParam("id") long id);

	@GET
	@Path("search")
	@Override
	List<AircraftType> findAircraftTypes(@QueryParam("iataCode") String iataCode,
			@QueryParam("icaoCode") String icaoCode);
}
