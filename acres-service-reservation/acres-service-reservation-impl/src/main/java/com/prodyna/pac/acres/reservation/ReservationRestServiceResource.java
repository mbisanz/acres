package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

@RequestScoped
@Logged
public class ReservationRestServiceResource implements ReservationRestService {

	@Inject
	@Unsecured
	ReservationService reservationService;

	@Override
	public List<Reservation> readAllReservations() {
		return reservationService.readAllReservations();
	}

	@Override
	public Reservation readReservation(long id) {
		return reservationService.readReservation(id);
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		return reservationService.createReservation(reservation);
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		return reservationService.updateReservation(reservation);
	}

	@Override
	public void deleteReservation(long id) {
		reservationService.deleteReservation(id);
	}

	@Override
	public List<Reservation> findReservations(String login,
			String aircraftRegistration, List<ReservationState> states) {
		return reservationService.findReservations(login, aircraftRegistration,
				states);
	}
}
