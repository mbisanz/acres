package com.prodyna.pac.acres.reservation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.qualifier.Current;
import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Monitored;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.license.License;
import com.prodyna.pac.acres.license.LicenseService;
import com.prodyna.pac.acres.reservation.exception.NoValidLicenseException;
import com.prodyna.pac.acres.reservation.exception.OverlappingReservationExistsException;
import com.prodyna.pac.acres.reservation.exception.ReservationNotValidException;
import com.prodyna.pac.acres.reservation.exception.WrongOwnerException;
import com.prodyna.pac.acres.reservation.exception.WrongStateException;
import com.prodyna.pac.acres.user.User;

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
	@Current
	private Instance<Date> currentDate;

	@Inject
	@Unsecured
	private LicenseService licenseService;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Override
	public List<Reservation> readUserReservations() {
		return reservationService.findReservations(user.getLogin(), null, null);
	}

	@Override
	public Reservation createUserReservation(Reservation reservation) {
		List<License> licenses = licenseService.findLicenses(user.getLogin(),
				reservation.getAircraft().getType().getIataCode());
		if (!reservationWithinLicense(reservation, licenses)) {
			throw new NoValidLicenseException("No valid license");
		}
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
		Date now = currentDate.get();
		if (!dateWithinReservation(reservation, now)) {
			throw new ReservationNotValidException();
		}
		return reservationService.updateReservation(reservation);
	}

	private boolean dateWithinReservation(Reservation reservation, Date date) {
		return reservation.getValidFrom().compareTo(date) <= 0
				&& reservation.getValidTo().compareTo(date) >= 0;
	}

	private boolean reservationWithinLicense(Reservation reservation,
			List<License> licenses) {
		if (licenses == null) {
			return false;
		}
		for (License license : licenses) {
			if (reservationWithinLicense(reservation, license)) {
				return true;
			}
		}
		return false;
	}

	private boolean reservationWithinLicense(Reservation reservation,
			License license) {
		boolean licenseBeginsBefore = license.getValidFrom() == null
				|| license.getValidFrom().compareTo(reservation.getValidFrom()) <= 0;
		boolean licenseEndsAfter = license.getValidTo() == null
				|| license.getValidTo().compareTo(reservation.getValidTo()) >= 0;
		return licenseBeginsBefore && licenseEndsAfter;
	}

	private Reservation loadReservation(long reservationId) {
		return reservationService.readReservation(reservationId);
	}
}
