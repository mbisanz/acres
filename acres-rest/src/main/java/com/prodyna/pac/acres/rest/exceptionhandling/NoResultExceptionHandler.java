package com.prodyna.pac.acres.rest.exceptionhandling;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionHandler implements
		ExceptionMapper<NoResultException> {

	@Override
	public Response toResponse(NoResultException exception) {
		return Response.status(Status.BAD_REQUEST)
				.entity(exception.getMessage()).build();
	}
}
