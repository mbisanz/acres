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
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.reservation.exception.NoValidLicenseException;
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

		Reservation result = reservationWorkflowService
				.createUserReservation(res);
		Assert.assertNotNull(result.getId());
	}

	@Test(expected = NoValidLicenseException.class)
	public void testAddReservationNoLicense() throws Exception {
		Aircraft aircraft = aircraftService.findAircraft("N668US");
		Assert.assertNotNull(aircraft);

		Date now = now();
		Reservation res = new Reservation();
		res.setAircraft(aircraft);
		res.setValidFrom(now);
		res.setValidTo(plusHours(now, 1));

		reservationWorkflowService.createUserReservation(res);
	}

	private Date now() {
		return new Date();
	}

	private Date plusHours(Date when, int hours) {
		return new Date(when.getTime() + (hours * 60L * 60L * 1000L));
	}
}
