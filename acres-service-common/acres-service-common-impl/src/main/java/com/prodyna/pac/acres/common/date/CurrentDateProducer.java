package com.prodyna.pac.acres.common.date;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.prodyna.pac.acres.common.qualifier.Current;

/**
 * CDI producer for the current date, to allow for testing with a fixed current date.
 * 
 * @author Martin Bisanz, PRODYNA AG
 */
@ApplicationScoped
public class CurrentDateProducer {

	@Produces
	@Current
	public Date produceCurrentDate() {
		return new Date();
	}
}
