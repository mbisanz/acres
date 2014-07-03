package com.prodyna.pac.acres.reservation.exception;

import com.prodyna.pac.acres.common.exception.AcresException;

public class WrongOwnerException extends AcresException {

	private static final long serialVersionUID = 1L;

	public WrongOwnerException() {
		super("Wrong owner");
	}

	public WrongOwnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongOwnerException(String message) {
		super(message);
	}

	public WrongOwnerException(Throwable cause) {
		super(cause);
	}
}
