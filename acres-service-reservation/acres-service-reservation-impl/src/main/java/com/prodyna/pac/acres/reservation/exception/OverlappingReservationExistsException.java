package com.prodyna.pac.acres.reservation.exception;

import com.prodyna.pac.acres.common.exception.AcresException;
import com.prodyna.pac.acres.reservation.Reservation;

public class OverlappingReservationExistsException extends AcresException {

	private static final long serialVersionUID = 1L;

	private final Reservation reservation;

	public OverlappingReservationExistsException(Reservation reservation) {
		super("Overlapping reservation exists");
		this.reservation = reservation;
	}

	public OverlappingReservationExistsException(String message,
			Throwable cause, Reservation reservation) {
		super(message, cause);
		this.reservation = reservation;
	}

	public OverlappingReservationExistsException(String message,
			Reservation reservation) {
		super(message);
		this.reservation = reservation;
	}

	public OverlappingReservationExistsException(Throwable cause,
			Reservation reservation) {
		super(cause);
		this.reservation = reservation;
	}

	public Reservation getReservation() {
		return reservation;
	}
}
