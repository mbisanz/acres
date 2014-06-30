package com.prodyna.pac.acres.reservation.cleanup;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.qualifier.Current;
import com.prodyna.pac.acres.common.qualifier.Monitored;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.reservation.Reservation;
import com.prodyna.pac.acres.reservation.ReservationService;
import com.prodyna.pac.acres.reservation.ReservationState;

@Singleton
@Monitored
public class ReservationAutoReturnBean {

	@Inject
	private Logger log;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Inject
	@Current
	private Instance<Date> currentDate;

	@Schedule(second = "0", minute = "*/1", hour = "*", persistent = false)
	public void checkExpiredReservations() {
		Date now = currentDate.get();
		log.debug("Running expiration check, date {}", now);
		List<Reservation> openReservations = reservationService.findReservations(null, null,
				Arrays.asList(new ReservationState[] { ReservationState.RESERVED, ReservationState.LENT }));
		for (Reservation reservation : openReservations) {
			if (now.compareTo(reservation.getValidTo()) >= 0) {
				reservation.setState(ReservationState.RETURNED);
				reservationService.updateReservation(reservation);
				log.debug("Updated reservation {}", reservation);
			}
		}
	}
}
