package com.prodyna.pac.acres.common.exception;

public class AcresException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AcresException() {
		super();
	}

	public AcresException(String message, Throwable cause) {
		super(message, cause);
	}

	public AcresException(String message) {
		super(message);
	}

	public AcresException(Throwable cause) {
		super(cause);
	}
}
