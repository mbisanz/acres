package com.prodyna.pac.acres.aircraft;

import java.util.List;
import java.util.logging.Logger;

import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;

@RunWith(Arquillian.class)
public class AircraftTypeServiceTest extends AbstractAcresTest {

	@Inject
	private Logger log;

	@Inject
	private AircraftTypeService service;

	@Ignore
	@Test
	@InSequence(1)
	public void test() throws Exception {
		List<AircraftType> before = service.readAllAircraftTypes();
		Assert.assertTrue(before.isEmpty());

		AircraftType type = new AircraftType();
		type.setName("Fokker 50");
		type.setIataCode("F50");
		type.setIcaoCode("F50");
		service.createAircraftType(type);

		Assert.assertNotNull(type.getId());

		List<AircraftType> after = service.readAllAircraftTypes();
		Assert.assertEquals(1, after.size());
	}
}
