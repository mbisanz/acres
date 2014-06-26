package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@RequestScoped
@Logged
public class ReservationWorkflowRestServiceResource implements
		ReservationWorkflowRestService {

	@Inject
	@Unsecured
	ReservationWorkflowService reservationWorkflowService;

	@Override
	public List<Reservation> readMyReservations() {
		return reservationWorkflowService.readMyReservations();
	}

	@Override
	public Reservation addReservation(Reservation reservation) {
		return reservationWorkflowService.addReservation(reservation);
	}

	@Override
	public Reservation cancelReservation(long reservationId) {
		return reservationWorkflowService.cancelReservation(reservationId);
	}

	@Override
	public Reservation checkoutOrReturnAircraft(long reservationId) {
		return reservationWorkflowService
				.checkoutOrReturnAircraft(reservationId);
	}
}
