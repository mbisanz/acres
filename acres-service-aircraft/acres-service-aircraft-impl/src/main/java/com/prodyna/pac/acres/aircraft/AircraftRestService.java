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
import javax.ws.rs.core.MediaType;

@Path("ac")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AircraftRestService extends AircraftService {

	@GET
	@Override
	List<Aircraft> readAllAircrafts();

	@GET
	@Path("{id:[0-9][0-9]*}")
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
	Aircraft findAircraft(@QueryParam("registration") String registration);
}