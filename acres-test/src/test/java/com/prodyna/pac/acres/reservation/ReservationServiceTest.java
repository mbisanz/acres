package com.prodyna.pac.acres.reservation;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.exception.AcresValidationException;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@RunWith(Arquillian.class)
public class ReservationServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Inject
	@Unsecured
	private ReservationService reservationService;

	@Test(expected = EJBTransactionRolledbackException.class)
	public void testCreateReservationNoUser() throws Exception {
		Aircraft ac1 = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(ac1);

		Reservation reservation = new Reservation();
		reservation.setAircraft(ac1);
		reservation.setValidFrom(showcaseService.getDateTime("2014-07-04 11:00"));
		reservation.setValidTo(showcaseService.getDateTime("2014-07-04 11:00"));
		reservationService.createReservation(reservation);
	}

	@Test(expected = EJBTransactionRolledbackException.class)
	public void testCreateReservationNoAircraft() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		Reservation reservation = new Reservation();
		reservation.setUser(pilot1);
		reservation.setValidFrom(showcaseService.getDateTime("2014-07-04 11:00"));
		reservation.setValidTo(showcaseService.getDateTime("2014-07-04 11:00"));
		reservationService.createReservation(reservation);
	}

	@Test(expected = AcresValidationException.class)
	public void testCreateReservationWrongDates() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		Reservation reservation = new Reservation();
		reservation.setUser(pilot1);
		reservation.setValidFrom(showcaseService.getDateTime("2014-07-04 11:00"));
		reservation.setValidTo(showcaseService.getDateTime("2014-07-04 10:00"));
		reservationService.createReservation(reservation);
	}

	@Test
	public void testCreateReservation() throws Exception {
		List<Reservation> before = reservationService.readAllReservations();
		Assert.assertEquals(2, before.size());

		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		Aircraft ac1 = aircraftService.findAircraft("VH-FNA");
		Assert.assertNotNull(ac1);

		Reservation reservation = new Reservation();
		reservation.setUser(pilot1);
		reservation.setAircraft(ac1);
		reservation.setValidFrom(showcaseService.getDateTime("2014-07-04 11:00"));
		reservation.setValidTo(showcaseService.getDateTime("2014-07-04 11:00"));
		reservation.setState(ReservationState.RESERVED);
		reservationService.createReservation(reservation);

		Assert.assertNotNull(reservation.getId());

		List<Reservation> result = reservationService.readAllReservations();
		Assert.assertEquals(3, result.size());
	}

	@Test
	public void testFindReservation() throws Exception {
		List<Reservation> before = reservationService.readAllReservations();
		Assert.assertEquals(2, before.size());

		List<Reservation> result;
		result = reservationService.findReservations(null, null, null);
		Assert.assertEquals(2, result.size());

		result = reservationService.findReservations("pilot1", null, null);
		Assert.assertEquals(1, result.size());

		result = reservationService.findReservations(null, "VH-FNA", null);
		Assert.assertEquals(1, result.size());

		result = reservationService.findReservations(null, "VH-FNA",
				Arrays.asList(new ReservationState[] { ReservationState.CANCELLED }));
		Assert.assertEquals(0, result.size());

		result = reservationService.findReservations(null, "VH-FNA",
				Arrays.asList(new ReservationState[] { ReservationState.CANCELLED, ReservationState.RESERVED }));
		Assert.assertEquals(1, result.size());
	}
}
