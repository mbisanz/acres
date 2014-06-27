package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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

@Path("res")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReservationRestService extends ReservationService {

	@GET
	@Override
	@RolesAllowed("admin")
	List<Reservation> readAllReservations();

	@GET
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed({ "admin", "user" })
	@Override
	Reservation readReservation(@PathParam("id") long id);

	@POST
	@RolesAllowed("admin")
	@Override
	Reservation createReservation(Reservation reservation);

	@PUT
	@RolesAllowed("admin")
	@Override
	Reservation updateReservation(Reservation reservation);

	@DELETE
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	@Override
	void deleteReservation(@PathParam("id") long id);

	@GET
	@Path("search")
	@RolesAllowed({ "admin", "user" })
	@Override
	List<Reservation> findReservations(@QueryParam("login") String login,
			@QueryParam("registration") String aircraftRegistration,
			@QueryParam("state") List<ReservationState> states);
}
