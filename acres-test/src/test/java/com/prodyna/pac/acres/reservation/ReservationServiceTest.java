package com.prodyna.pac.acres.reservation;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.TestScenarioBean;
import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@RunWith(Arquillian.class)
public class ReservationServiceTest {

	@Inject
	TestScenarioBean testScenario;

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Before
	public void resetTestSzenario() throws Exception {
		testScenario.setup();
	}

	@Test(expected = Exception.class)
	public void testCreateReservationNoUser() throws Exception {
		Reservation user = new Reservation();
		reservationService.createReservation(user);
	}

	@Test(expected = Exception.class)
	public void testCreateReservationNoAircraft() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		Reservation reservation = new Reservation();
		reservation.setUser(pilot1);
		reservationService.createReservation(reservation);
	}

	@Test
	public void testCreateReservation() throws Exception {
		List<Reservation> before = reservationService.readAllReservations();
		Assert.assertEquals(2, before.size());

		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		Aircraft cna = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(cna);

		Reservation reservation = new Reservation();
		reservation.setUser(pilot1);
		reservation.setAircraft(cna);
		reservation.setValidFrom(testScenario.getDateTime("2014-07-04 11:00"));
		reservation.setValidTo(testScenario.getDateTime("2014-07-04 11:00"));
		reservation.setState(ReservationState.RESERVED);
		reservationService.createReservation(reservation);

		Assert.assertNotNull(reservation.getId());

		List<Reservation> result = reservationService.readAllReservations();
		Assert.assertEquals(3, result.size());
	}
}
