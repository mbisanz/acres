package com.prodyna.pac.acres.aircraft;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.common.security.Unsecured;

@RunWith(Arquillian.class)
public class AircraftServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Test
	@InSequence(101)
	public void testCreateAircraftType() throws Exception {
		login("admin", "admin");

		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertTrue(before.isEmpty());

		AircraftType type1 = new AircraftType();
		type1.setName("Fokker 50");
		type1.setIataCode("F50");
		aircraftTypeService.createAircraftType(type1);
		Assert.assertNotNull(type1.getId());

		AircraftType type2 = new AircraftType();
		type2.setName("Cessna");
		type2.setIataCode("CNA");
		aircraftTypeService.createAircraftType(type2);
		Assert.assertNotNull(type2.getId());

		AircraftType type3 = new AircraftType();
		type3.setName("Boeing 747-300");
		type3.setIataCode("74U");
		type3.setIcaoCode("B743");
		aircraftTypeService.createAircraftType(type3);
		Assert.assertNotNull(type3.getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, result.size());
	}

	@Test
	@InSequence(102)
	public void testUpdateAircraftType() throws Exception {
		login("admin", "admin");

		AircraftType type = aircraftTypeService.readAircraftType(1);
		Assert.assertNotNull(type);
		Assert.assertEquals("F50", type.getIataCode());
		Assert.assertNull(type.getIcaoCode());
		type.setIcaoCode("F50");

		AircraftType result = aircraftTypeService.updateAircraftType(type);
		Assert.assertNotSame(type, result);
		Assert.assertEquals("F50", result.getIcaoCode());
	}

	@Test
	@InSequence(103)
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

	@Test
	@InSequence(201)
	public void testCreateAircraft() throws Exception {
		login("admin", "admin");

		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertTrue(before.isEmpty());

		AircraftType type = aircraftTypeService.findAircraftTypes("CNA", null).get(0);

		Aircraft ac = new Aircraft();
		ac.setRegistration("VH-FNA");
		ac.setType(type);
		aircraftService.createAircraft(ac);
		Assert.assertNotNull(ac.getId());

		List<Aircraft> result = aircraftService.readAllAircrafts();
		Assert.assertEquals(1, result.size());
	}

	@Test
	@InSequence(202)
	public void testUpdateAircraft() throws Exception {
		login("admin", "admin");

		Aircraft ac = aircraftService.findAircrafts("VH-FNA").get(0);

		AircraftType type = aircraftTypeService.findAircraftTypes("F50", null).get(0);
		ac.setType(type);
		Aircraft result = aircraftService.updateAircraft(ac);

		Assert.assertNotSame(ac, result);
		Assert.assertEquals("F50", result.getType().getIataCode());
	}

	@Ignore
	@Test
	@InSequence(801)
	public void testDeleteAircraft() throws Exception {
		login("admin", "admin");

		List<Aircraft> before = aircraftService.readAllAircrafts();
		Assert.assertEquals(1, before.size());

		aircraftService.deleteAircraft(before.get(0).getId());

		List<Aircraft> result = aircraftService.readAllAircrafts();
		Assert.assertEquals(0, result.size());
	}

	@Test
	@InSequence(901)
	public void testDeleteAircraftType() throws Exception {
		login("admin", "admin");

		List<AircraftType> before = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(3, before.size());

		aircraftTypeService.deleteAircraftType(before.get(2).getId());

		List<AircraftType> result = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(2, result.size());
	}
}
