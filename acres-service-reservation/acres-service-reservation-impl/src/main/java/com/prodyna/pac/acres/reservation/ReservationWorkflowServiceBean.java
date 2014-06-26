package com.prodyna.pac.acres.reservation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.reservation.exception.OverlappingReservationExistsException;
import com.prodyna.pac.acres.reservation.exception.ReservationNotValidException;
import com.prodyna.pac.acres.reservation.exception.WrongOwnerException;
import com.prodyna.pac.acres.reservation.exception.WrongStateException;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.context.Current;

@Unsecured
@Stateless
@Logged
@Monitored
public class ReservationWorkflowServiceBean implements
		ReservationWorkflowService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	@Current
	private User user;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Override
	public List<Reservation> readMyReservations() {
		return reservationService.findReservations(user.getLogin(), null, null);
	}

	@Override
	public Reservation addReservation(Reservation reservation) {
		List<Reservation> existingReservations = reservationService
				.findReservations(null, reservation.getAircraft()
						.getRegistration(), Arrays
						.asList(new ReservationState[] {
								ReservationState.RESERVED,
								ReservationState.LENT }));
		for (Reservation existing : existingReservations) {
			if (existing.overlaps(reservation)) {
				throw new OverlappingReservationExistsException(reservation);
			}
		}
		reservation.setUser(user);
		reservation.setState(ReservationState.RESERVED);
		return reservationService.createReservation(reservation);
	}

	@Override
	public Reservation cancelReservation(long reservationId) {
		Reservation reservation = loadReservation(reservationId);
		if (!user.equals(reservation.getUser())) {
			throw new WrongOwnerException();
		}
		if (!ReservationState.RESERVED.equals(reservation.getState())) {
			throw new WrongStateException();
		}
		reservation.setState(ReservationState.CANCELLED);
		return reservationService.updateReservation(reservation);
	}

	@Override
	public Reservation checkoutOrReturnAircraft(long reservationId) {
		Reservation reservation = loadReservation(reservationId);
		if (!user.equals(reservation.getUser())) {
			throw new WrongOwnerException();
		}
		if (ReservationState.RESERVED.equals(reservation.getState())) {
			reservation.setState(ReservationState.LENT);
		} else if (ReservationState.LENT.equals(reservation.getState())) {
			reservation.setState(ReservationState.RETURNED);
		} else {
			throw new WrongStateException();
		}
		Date now = now();
		if (!(reservation.getValidFrom().compareTo(now) <= 0 && reservation
				.getValidTo().compareTo(now) >= 0)) {
			throw new ReservationNotValidException();
		}
		return reservationService.updateReservation(reservation);
	}

	private Reservation loadReservation(long reservationId) {
		return reservationService.readReservation(reservationId);
	}

	private Date now() {
		// TODO use cdi producer for current date
		return new Date();
	}
}
