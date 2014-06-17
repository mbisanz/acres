package com.prodyna.pac.acres.license;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@Stateless
@Logged
public class LicenseRestServiceResource implements LicenseRestService {

	@Inject
	@Unsecured
	LicenseService service;

	@RolesAllowed("admin")
	@Override
	public License readLicense(long id) {
		return service.readLicense(id);
	}

	@RolesAllowed("admin")
	@Override
	public List<License> readAllLicenses() {
		return service.readAllLicenses();
	}

	@RolesAllowed("admin")
	@Override
	public License createLicense(License License) {
		return service.createLicense(License);
	}

	@RolesAllowed("admin")
	@Override
	public License updateLicense(License License) {
		return service.updateLicense(License);
	}

	@RolesAllowed("admin")
	@Override
	public void deleteLicense(long id) {
		service.deleteLicense(id);
	}

	@RolesAllowed("admin")
	@Override
	public List<License> findLicenses(String login, String aircraftType) {
		return service.findLicenses(login, aircraftType);
	}
}
