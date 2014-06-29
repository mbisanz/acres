package com.prodyna.pac.acres.rest.exceptionhandling;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import com.prodyna.pac.acres.common.exception.AcresException;

@Provider
public class AcresExceptionHandler implements ExceptionMapper<AcresException> {

	@Inject
	private Logger log;

	@Override
	public Response toResponse(AcresException exception) {
		log.warn("Handling business exception {}: {}", exception.getClass(),
				exception.getMessage());
		log.debug("Business exception was:", exception);
		return Response.status(Status.BAD_REQUEST)
				.entity(exception.getMessage()).build();
	}
}
