package com.prodyna.pac.acres.common.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI producer for SLF4j logger.
 * 
 * @author Martin Bisanz, PRODYNA AG
 */
@ApplicationScoped
public class LoggerProducer {

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember()
				.getDeclaringClass().getName());
	}

}
