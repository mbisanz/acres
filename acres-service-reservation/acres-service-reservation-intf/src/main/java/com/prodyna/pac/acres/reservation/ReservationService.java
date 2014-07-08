package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.validation.Valid;

public interface ReservationService {

	List<Reservation> readAllReservations();

	Reservation readReservation(long id);

	Reservation createReservation(@Valid Reservation reservation);

	Reservation updateReservation(@Valid Reservation reservation);

	void deleteReservation(long id);

	List<Reservation> findReservations(String login, String aircraftRegistration, List<ReservationState> states);
}
