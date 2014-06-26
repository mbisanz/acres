package com.prodyna.pac.acres.reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

public class ReservationTest {

	@Test
	public void testOverlaps() throws Exception {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		Reservation res_0800_0900 = new Reservation();
		res_0800_0900.setValidFrom(format.parse("2014-07-01 08:00"));
		res_0800_0900.setValidTo(format.parse("2014-07-01 09:00"));

		Reservation res_0830_0930 = new Reservation();
		res_0830_0930.setValidFrom(format.parse("2014-07-01 08:30"));
		res_0830_0930.setValidTo(format.parse("2014-07-01 09:30"));

		Reservation res_0900_1000 = new Reservation();
		res_0900_1000.setValidFrom(format.parse("2014-07-01 09:00"));
		res_0900_1000.setValidTo(format.parse("2014-07-01 10:00"));

		Reservation res_0700_1000 = new Reservation();
		res_0700_1000.setValidFrom(format.parse("2014-07-01 07:00"));
		res_0700_1000.setValidTo(format.parse("2014-07-01 10:00"));

		Assert.assertFalse(res_0800_0900.overlaps(res_0900_1000));
		Assert.assertFalse(res_0900_1000.overlaps(res_0800_0900));

		Assert.assertTrue(res_0830_0930.overlaps(res_0900_1000));
		Assert.assertTrue(res_0900_1000.overlaps(res_0830_0930));

		Assert.assertTrue(res_0700_1000.overlaps(res_0800_0900));
		Assert.assertTrue(res_0700_1000.overlaps(res_0830_0930));
		Assert.assertTrue(res_0700_1000.overlaps(res_0900_1000));
		Assert.assertTrue(res_0800_0900.overlaps(res_0700_1000));
		Assert.assertTrue(res_0830_0930.overlaps(res_0700_1000));
		Assert.assertTrue(res_0900_1000.overlaps(res_0700_1000));
	}
}
