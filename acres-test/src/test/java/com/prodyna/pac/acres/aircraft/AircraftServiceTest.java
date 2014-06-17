package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.TestScenarioBean;
import com.prodyna.pac.acres.common.security.Unsecured;

@RunWith(Arquillian.class)
public class AircraftServiceTest {

	@Inject
	TestScenarioBean testScenario;

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Before
	public void resetTestSzenario() throws Exception {
		testScenario.setup();
	}

	@Test
	public void testCreateAircraftType() throws Exception {
		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		AircraftType type3 = new AircraftType();
		type3.setName("Boeing 747-400");
		type3.setIataCode("744");
		type3.setIcaoCode("B744");
		aircraftTypeService.createAircraftType(type3);
		Assert.assertNotNull(type3.getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(4, result.size());
	}

	@Test
	public void testUpdateAircraftType() throws Exception {
		AircraftType type = aircraftTypeService.findAircraftTypes("F50", null).get(0);
		Assert.assertNotNull(type);
		Assert.assertEquals("F50", type.getIataCode());
		Assert.assertEquals("F50", type.getIcaoCode());
		type.setIcaoCode("F51");

		AircraftType result = aircraftTypeService.updateAircraftType(type);
		Assert.assertNotSame(type, result);
		Assert.assertEquals("F51", result.getIcaoCode());
	}

	@Test
	public void testDeleteAircraftType() throws Exception {
		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		AircraftType type = aircraftTypeService.findAircraftTypes("CNA", null).get(0);
		Assert.assertNotNull(type);
		aircraftTypeService.deleteAircraftType(type.getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testCreateAircraft() throws Exception {
		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertEquals(3, before.size());

		AircraftType type = aircraftTypeService.findAircraftTypes("CNA", null).get(0);

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
		Aircraft ac = aircraftService.findAircrafts("VH-FNA").get(0);

		AircraftType type = aircraftTypeService.findAircraftTypes("F50", null).get(0);
		ac.setType(type);
		Aircraft result = aircraftService.updateAircraft(ac);

		Assert.assertNotSame(ac, result);
		Assert.assertEquals("F50", result.getType().getIataCode());
	}

	@Test
	public void testDeleteAircraft() throws Exception {
		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertEquals(3, before.size());

		aircraftService.deleteAircraft(before.get(0).getId());

		List<Aircraft> result = aircraftService.readAllAircrafts();
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testFindAircraftType() throws Exception {
		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		List<AircraftType> result1 = aircraftTypeService.findAircraftTypes(null, null);
		Assert.assertEquals(3, result1.size());

		List<AircraftType> result2 = aircraftTypeService.findAircraftTypes(null, "F50");
		Assert.assertEquals(1, result2.size());

		List<AircraftType> result3 = aircraftTypeService.findAircraftTypes("notexist", "F50");
		Assert.assertEquals(0, result3.size());
	}
}
