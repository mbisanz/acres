package com.prodyna.pac.acres.reservation;

import java.util.Date;

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.user.User;

public interface ReservationWorkflowService {

	void addReservation(User user, Aircraft aircraft, Date validFrom, Date validTo);

	void cancelReservation(User user, long reservationId);

	void checkoutAircraft(User user, long reservationId);

	void returnAircraft(User user, long reservationId);
}
