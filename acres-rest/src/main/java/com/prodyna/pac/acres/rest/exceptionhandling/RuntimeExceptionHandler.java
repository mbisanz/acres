package com.prodyna.pac.acres.rest.exceptionhandling;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class RuntimeExceptionHandler implements
		ExceptionMapper<RuntimeException> {

	@Inject
	private Logger log;

	@Override
	public Response toResponse(RuntimeException exception) {
		log.error("Runtime exception", exception);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getMessage()).build();
	}
}
