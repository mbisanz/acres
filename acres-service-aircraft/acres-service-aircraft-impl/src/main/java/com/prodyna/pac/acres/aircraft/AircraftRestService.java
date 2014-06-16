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

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;

@Path("ac")
@Produces("application/json")
@Consumes("application/json")
public interface AircraftRestService extends AircraftService {

	@GET
	@Override
	List<Aircraft> readAllAircrafts();

	@GET
	@Path("{id}")
	@Override
	Aircraft readAircraft(@PathParam("id") long id);

	@POST
	@Override
	Aircraft createAircraft(Aircraft aircraft);

	@PUT
	@Override
	Aircraft updateAircraft(Aircraft aircraft);

	@DELETE
	@Path("{id}")
	@Override
	void deleteAircraft(@PathParam("id") long id);

	@GET
	@Path("search")
	@Override
	List<Aircraft> findAircrafts(@QueryParam("registration") String registration);
}
