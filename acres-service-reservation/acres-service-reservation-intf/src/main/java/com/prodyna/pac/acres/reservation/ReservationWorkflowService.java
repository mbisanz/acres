package com.prodyna.pac.acres.reservation;

import java.util.List;

public interface ReservationWorkflowService {

	List<Reservation> readUserReservations();

	Reservation createUserReservation(Reservation reservation);

	Reservation cancelReservation(long reservationId);

	Reservation checkoutOrReturnAircraft(long reservationId);
}
