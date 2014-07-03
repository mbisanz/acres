package com.prodyna.pac.acres.reservation.exception;

import com.prodyna.pac.acres.common.exception.AcresException;

public class ReservationNotValidException extends AcresException {

	private static final long serialVersionUID = 1L;

	public ReservationNotValidException() {
		super("Reservation is not valid");
	}

	public ReservationNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservationNotValidException(String message) {
		super(message);
	}

	public ReservationNotValidException(Throwable cause) {
		super(cause);
	}
}
