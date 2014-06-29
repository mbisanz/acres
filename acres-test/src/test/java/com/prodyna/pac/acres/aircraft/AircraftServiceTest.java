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
public class AircraftServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Test
	public void testCreateAircraft() throws Exception {
		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertEquals(3, before.size());

		AircraftType type = aircraftTypeService.findAircraftType("CNA");

		Aircraft ac = new Aircraft();
		ac.setRegistration("D-ABEA");
		ac.setType(type);
		aircraftService.createAircraft(ac);
		Assert.assertNotNull(ac.getId());

		List<Aircraft> result = aircraftService.readAllAircrafts();
		Assert.assertEquals(4, result.size());
	}

	@Test
	public void testUpdateAircraft() throws Exception {
		Aircraft ac = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(ac);

		AircraftType type = aircraftTypeService.findAircraftType("F50");
		ac.setType(type);
		Aircraft result = aircraftService.updateAircraft(ac);

		Assert.assertNotSame(ac, result);
		Assert.assertEquals("F50", result.getType().getIataCode());
	}

	@Test
	public void testDeleteAircraft() throws Exception {
		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertEquals(3, before.size());

		aircraftService.deleteAircraft(aircraftService.findAircraft("9M-MPS")
				.getId());

		List<Aircraft> result = aircraftService.readAllAircrafts();
		Assert.assertEquals(2, result.size());
	}
}
