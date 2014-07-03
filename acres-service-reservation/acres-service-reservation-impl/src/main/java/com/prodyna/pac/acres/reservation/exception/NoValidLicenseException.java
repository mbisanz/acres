package com.prodyna.pac.acres.reservation.exception;

import com.prodyna.pac.acres.common.exception.AcresException;

public class NoValidLicenseException extends AcresException {

	private static final long serialVersionUID = 1L;

	public NoValidLicenseException() {
		super("No valid license");
	}

	public NoValidLicenseException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoValidLicenseException(String message) {
		super(message);
	}

	public NoValidLicenseException(Throwable cause) {
		super(cause);
	}
}
