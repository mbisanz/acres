package com.prodyna.pac.acres.rest.exceptionhandling;

import javax.ejb.EJBTransactionRolledbackException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBTransactionRolledbackExceptionHandler implements
		ExceptionMapper<EJBTransactionRolledbackException> {

	@Override
	public Response toResponse(EJBTransactionRolledbackException exception) {
		Exception cause = findCause(exception, 10);
		if (cause instanceof ValidationException) {
			return Response.status(Status.BAD_REQUEST)
					.entity(cause.getMessage()).build();
		}
		if ("org.hibernate.exception.ConstraintViolationException".equals(cause
				.getClass().getName())) {
			String message = cause.getMessage()
					+ (cause.getCause() == null ? "" : ": "
							+ cause.getCause().getMessage());
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getMessage()).build();
	}

	private Exception findCause(Exception e, int maxRecursion) {
		Throwable t = e.getCause();
		if (t == null) {
			return e;
		}
		if (!(t instanceof Exception)) {
			return e;
		}
		Exception cause = (Exception) t;
		if ("org.hibernate.exception.ConstraintViolationException".equals(cause
				.getClass().getName())) {
			return cause;
		}
		if (t.equals(e)) {
			return e;
		}
		if (maxRecursion < 0) {
			return e;
		}
		return findCause(cause, maxRecursion - 1);
	}
}
