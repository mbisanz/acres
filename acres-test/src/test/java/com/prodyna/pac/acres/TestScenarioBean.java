package com.prodyna.pac.acres;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.junit.Assert;

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.aircraft.AircraftType;
import com.prodyna.pac.acres.aircraft.AircraftTypeService;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.license.License;
import com.prodyna.pac.acres.license.LicenseService;
import com.prodyna.pac.acres.reservation.Reservation;
import com.prodyna.pac.acres.reservation.ReservationService;
import com.prodyna.pac.acres.reservation.ReservationState;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Stateless
public class TestScenarioBean {

	@Inject
	@Unsecured
	protected UserService userService;

	@Inject
	@Unsecured
	protected AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	protected AircraftService aircraftService;

	@Inject
	@Unsecured
	protected LicenseService licenseService;

	@Inject
	@Unsecured
	protected ReservationService reservationService;

	public void setup() throws Exception {
		resetDatabase();
		setupUsers();
		setupAircraftTypes();
		setupAircrafts();
		setupLicenses();
		setupReservations();
	}

	protected void resetDatabase() throws Exception {
		List<Reservation> reservations = reservationService
				.readAllReservations();
		for (Reservation res : reservations) {
			reservationService.deleteReservation(res.getId());
		}
		List<License> licenses = licenseService.readAllLicenses();
		for (License lic : licenses) {
			licenseService.deleteLicense(lic.getId());
		}
		List<User> users = userService.readAllUsers();
		for (User user : users) {
			userService.deleteUser(user.getId());
		}
		List<Aircraft> aircrafts = aircraftService.readAllAircrafts();
		for (Aircraft ac : aircrafts) {
			aircraftService.deleteAircraft(ac.getId());
		}
		List<AircraftType> aircraftTypes = aircraftTypeService
				.readAllAircraftTypes();
		for (AircraftType act : aircraftTypes) {
			aircraftTypeService.deleteAircraftType(act.getId());
		}
	}

	protected void setupAircraftTypes() throws Exception {
		Assert.assertTrue(aircraftTypeService.readAllAircraftTypes().isEmpty());

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
		type3.setIataCode("743");
		aircraftTypeService.createAircraftType(type3);
		Assert.assertNotNull(type3.getId());

		Assert.assertEquals(3, aircraftTypeService.readAllAircraftTypes().size());
	}

	protected void setupAircrafts() throws Exception {
		Assert.assertTrue(aircraftService.readAllAircrafts().isEmpty());

		AircraftType f50 = aircraftTypeService.findAircraftType("F50");
		Assert.assertNotNull(f50);

		Aircraft ac1 = new Aircraft();
		ac1.setRegistration("VH-FNA");
		ac1.setType(f50);
		aircraftService.createAircraft(ac1);
		Assert.assertNotNull(ac1.getId());

		AircraftType b747 = aircraftTypeService.findAircraftType("743");
		Assert.assertNotNull(b747);

		Aircraft ac2a = new Aircraft();
		ac2a.setRegistration("N668US");
		ac2a.setType(b747);
		aircraftService.createAircraft(ac2a);
		Assert.assertNotNull(ac2a.getId());

		Aircraft ac2b = new Aircraft();
		ac2b.setRegistration("9M-MPS");
		ac2b.setType(b747);
		aircraftService.createAircraft(ac2b);
		Assert.assertNotNull(ac2b.getId());

		Assert.assertEquals(3, aircraftService.readAllAircrafts().size());
	}

	protected void setupUsers() throws Exception {
		Assert.assertTrue(userService.readAllUsers().isEmpty());

		User guest = new User();
		guest.setLogin("guest");
		guest.setPassword("guest");
		guest.setRoles(new HashSet<String>(Arrays.asList(new String[] { "guest" })));
		userService.createUser(guest);
		Assert.assertNotNull(guest.getId());

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("admin");
		admin.setName("Adam Admin");
		admin.setEmail("admin@acres.pac");
		admin.setRoles(new HashSet<String>(Arrays.asList(new String[] { "admin" })));
		userService.createUser(admin);
		Assert.assertNotNull(admin.getId());

		User pilot1 = new User();
		pilot1.setLogin("pilot1");
		pilot1.setPassword("pilot1");
		pilot1.setName("Paul Pilot");
		pilot1.setEmail("pilot1@acres.pac");
		pilot1.setRoles(new HashSet<String>(Arrays.asList(new String[] { "user" })));
		userService.createUser(pilot1);
		Assert.assertNotNull(pilot1.getId());

		User pilot2 = new User();
		pilot2.setLogin("pilot2");
		pilot2.setPassword("pilot2");
		pilot2.setName("Peter Pilot");
		pilot2.setEmail("pilot2@acres.pac");
		pilot2.setRoles(new HashSet<String>(Arrays.asList(new String[] { "user" })));
		userService.createUser(pilot2);
		Assert.assertNotNull(pilot2.getId());

		Assert.assertEquals(4, userService.readAllUsers().size());
	}

	protected void setupLicenses() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);
		User pilot2 = userService.findUser("pilot2");
		Assert.assertNotNull(pilot2);

		AircraftType f50 = aircraftTypeService.findAircraftType("F50");
		Assert.assertNotNull(f50);
		AircraftType b747 = aircraftTypeService.findAircraftType("743");
		Assert.assertNotNull(b747);

		License p1l1 = new License();
		p1l1.setUser(pilot1);
		p1l1.setAircraftType(f50);
		p1l1.setValidFrom(getDate("2014-01-01"));
		p1l1.setValidTo(getDate("2024-01-01"));
		licenseService.createLicense(p1l1);
		Assert.assertNotNull(p1l1.getId());

		License p2l1 = new License();
		p2l1.setUser(pilot2);
		p2l1.setAircraftType(f50);
		p2l1.setValidFrom(getDate("2002-01-01"));
		p2l1.setValidTo(getDate("2012-01-01"));
		licenseService.createLicense(p2l1);
		Assert.assertNotNull(p2l1.getId());

		License p2l2 = new License();
		p2l2.setUser(pilot2);
		p2l2.setAircraftType(b747);
		p2l2.setValidFrom(getDate("2012-01-01"));
		p2l2.setValidTo(getDate("2022-01-01"));
		licenseService.createLicense(p2l2);
		Assert.assertNotNull(p2l2.getId());

		Assert.assertEquals(3, licenseService.readAllLicenses().size());
	}

	protected void setupReservations() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);
		User pilot2 = userService.findUser("pilot2");
		Assert.assertNotNull(pilot2);

		Aircraft vhfna = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(vhfna);
		Aircraft n668us = aircraftService.findAircraft("N668US");
		Assert.assertNotNull(n668us);

		Reservation p1r1 = new Reservation();
		p1r1.setUser(pilot1);
		p1r1.setAircraft(vhfna);
		p1r1.setValidFrom(getDateTime("2014-07-04 07:15"));
		p1r1.setValidTo(getDateTime("2014-07-04 09:45"));
		p1r1.setState(ReservationState.RESERVED);
		reservationService.createReservation(p1r1);
		Assert.assertNotNull(p1r1.getId());

		Reservation p2r1 = new Reservation();
		p2r1.setUser(pilot2);
		p2r1.setAircraft(n668us);
		p2r1.setValidFrom(getDateTime("2014-07-04 08:00"));
		p2r1.setValidTo(getDateTime("2014-07-04 16:00"));
		p2r1.setState(ReservationState.RESERVED);
		reservationService.createReservation(p2r1);
		Assert.assertNotNull(p2r1.getId());

		Assert.assertEquals(2, reservationService.readAllReservations().size());
	}

	public Date getDate(String dateString) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.parse(dateString);
	}

	public Date getDateTime(String dateTimeString) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.parse(dateTimeString);
	}
}
