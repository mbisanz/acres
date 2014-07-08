package com.prodyna.pac.acres.common.exception;

public class AcresValidationException extends AcresException {

	private static final long serialVersionUID = 1L;

	public AcresValidationException() {
		super();
	}

	public AcresValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AcresValidationException(String message) {
		super(message);
	}

	public AcresValidationException(Throwable cause) {
		super(cause);
	}
}
