package com.prodyna.pac.acres.reservation.exception;

import com.prodyna.pac.acres.common.exception.AcresException;

public class WrongStateException extends AcresException {

	private static final long serialVersionUID = 1L;

	public WrongStateException() {
		super("Wrong state");
	}

	public WrongStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongStateException(String message) {
		super(message);
	}

	public WrongStateException(Throwable cause) {
		super(cause);
	}
}
