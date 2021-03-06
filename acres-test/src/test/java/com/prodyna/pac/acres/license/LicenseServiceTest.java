package com.prodyna.pac.acres.license;

import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.acres.AbstractAcresTest;
import com.prodyna.pac.acres.aircraft.AircraftType;
import com.prodyna.pac.acres.aircraft.AircraftTypeService;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@RunWith(Arquillian.class)
public class LicenseServiceTest extends AbstractAcresTest {

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftTypeService aircraftTypeService;

	@Inject
	@Unsecured
	private LicenseService licenseService;

	@Test(expected = EJBTransactionRolledbackException.class)
	public void testCreateLicenseNoUser() throws Exception {
		License user = new License();
		licenseService.createLicense(user);
	}

	@Test(expected = EJBTransactionRolledbackException.class)
	public void testCreateLicenseNoAircraftType() throws Exception {
		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		License license = new License();
		license.setUser(pilot1);
		licenseService.createLicense(license);
	}

	@Test
	public void testCreateLicense() throws Exception {
		List<License> before = licenseService.readAllLicenses();
		Assert.assertEquals(3, before.size());

		User pilot1 = userService.findUser("pilot1");
		Assert.assertNotNull(pilot1);

		AircraftType cna = aircraftTypeService.findAircraftType("CNA");
		Assert.assertNotNull(cna);

		License license = new License();
		license.setUser(pilot1);
		license.setAircraftType(cna);
		licenseService.createLicense(license);

		Assert.assertNotNull(license.getId());

		List<License> result = licenseService.readAllLicenses();
		Assert.assertEquals(4, result.size());
	}
}
