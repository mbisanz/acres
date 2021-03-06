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
import javax.ws.rs.core.MediaType;

@Path("reswf")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReservationWorkflowRestService extends ReservationWorkflowService {

	@GET
	@RolesAllowed({ "user", "admin" })
	@Override
	List<Reservation> readUserReservations();

	@POST
	@RolesAllowed({ "user", "admin" })
	@Override
	Reservation createUserReservation(Reservation reservation);

	@PUT
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed({ "user", "admin" })
	@Override
	Reservation updateUserReservation(Reservation reservation);

	@DELETE
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed({ "user", "admin" })
	@Override
	Reservation cancelUserReservation(@PathParam("id") long reservationId);

	@PUT
	@Path("step/{id:[0-9][0-9]*}")
	@RolesAllowed({ "user", "admin" })
	@Override
	Reservation nextWorkflowStep(@PathParam("id") long reservationId);
}
