package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.validation.Valid;

public interface ReservationWorkflowService {

	List<Reservation> readUserReservations();

	Reservation createUserReservation(@Valid Reservation reservation);

	Reservation updateUserReservation(@Valid Reservation reservation);

	Reservation cancelUserReservation(long reservationId);

	Reservation nextWorkflowStep(long reservationId);
}
