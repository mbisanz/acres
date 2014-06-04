package com.prodyna.pac.acres.aircraft;

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

@Path("acType")
@Produces("application/json")
@Consumes("application/json")
public interface AircraftTypeService {

	@GET
	List<AircraftType> readAllAircraftTypes();

	@GET
	@Path("{id}")
	AircraftType readAircraftType(@PathParam("id") long id);

	@POST
	AircraftType createAircraftType(AircraftType aircraftType);

	@PUT
	AircraftType updateAircraftType(AircraftType aircraftType);

	@DELETE
	@Path("{id}")
	void deleteAircraftType(@PathParam("id") long id);

	@GET
	@Path("search")
	List<AircraftType> findAircraftTypes(@QueryParam("iataCode") String iataCode,
			@QueryParam("icaoCode") String icaoCode);
}
