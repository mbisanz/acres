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

@Path("acType")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AircraftTypeRestService extends AircraftTypeService {

	@GET
	@Override
	List<AircraftType> readAllAircraftTypes();

	@GET
	@Path("{id:[0-9][0-9]*}")
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
	AircraftType findAircraftType(@QueryParam("iataCode") String iataCode);
}
