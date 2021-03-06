package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

@RequestScoped
@Logged
public class ReservationWorkflowRestServiceResource implements ReservationWorkflowRestService {

	@Inject
	@Unsecured
	ReservationWorkflowService reservationWorkflowService;

	@Override
	public List<Reservation> readUserReservations() {
		return reservationWorkflowService.readUserReservations();
	}

	@Override
	public Reservation createUserReservation(Reservation reservation) {
		return reservationWorkflowService.createUserReservation(reservation);
	}

	@Override
	public Reservation updateUserReservation(Reservation reservation) {
		return reservationWorkflowService.updateUserReservation(reservation);
	}

	@Override
	public Reservation cancelUserReservation(long reservationId) {
		return reservationWorkflowService.cancelUserReservation(reservationId);
	}

	@Override
	public Reservation nextWorkflowStep(long reservationId) {
		return reservationWorkflowService.nextWorkflowStep(reservationId);
	}
}
