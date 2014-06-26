package com.prodyna.pac.acres.reservation;

import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.UserService;

@RunWith(Arquillian.class)
public class ReservationWorkflowServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Inject
	@Unsecured
	private ReservationWorkflowService reservationWorkflowService;

	@Test
	public void testAddReservation() throws Exception {
		Aircraft aircraft = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(aircraft);

		Date now = now();
		Reservation res = new Reservation();
		res.setAircraft(aircraft);
		res.setValidFrom(now);
		res.setValidTo(plusHours(now, 1));
		reservationWorkflowService.addReservation(res);
	}

	private Date now() {
		return new Date();
	}

	private Date plusHours(Date when, int hours) {
		return new Date(when.getTime() + (hours * 60L * 60L * 1000L));
	}
}
