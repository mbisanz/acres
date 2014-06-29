package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

@RunWith(Arquillian.class)
public class AircraftTypeServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Test
	public void testCreateAircraftType() throws Exception {
		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		AircraftType type3 = new AircraftType();
		type3.setName("Boeing 747-400");
		type3.setIataCode("744");
		aircraftTypeService.createAircraftType(type3);
		Assert.assertNotNull(type3.getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(4, result.size());
	}

	@Test
	public void testUpdateAircraftType() throws Exception {
		AircraftType type = aircraftTypeService.findAircraftType("F50");
		Assert.assertNotNull(type);
		Assert.assertEquals("F50", type.getIataCode());
		type.setIataCode("F51");

		AircraftType result = aircraftTypeService.updateAircraftType(type);
		Assert.assertNotSame(type, result);
		Assert.assertEquals("F51", result.getIataCode());
	}

	@Test
	public void testDeleteAircraftType() throws Exception {
		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		AircraftType type = aircraftTypeService.findAircraftType("CNA");
		Assert.assertNotNull(type);
		aircraftTypeService.deleteAircraftType(type.getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(2, result.size());
	}
}
