package com.prodyna.pac.acres.reservation;

import java.util.List;

public interface ReservationService {

	List<Reservation> readAllReservations();

	Reservation readReservation(long id);

	Reservation createReservation(Reservation reservation);

	Reservation updateReservation(Reservation reservation);

	void deleteReservation(long id);

	List<Reservation> findReservations(String login, String aircraftRegistration, ReservationState state);
}
