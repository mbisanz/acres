package com.prodyna.pac.acres.license;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.security.Unsecured;

@RequestScoped
@Logged
public class LicenseRestServiceResource implements LicenseRestService {

	@Inject
	@Unsecured
	LicenseService service;

	@Override
	public License readLicense(long id) {
		return service.readLicense(id);
	}

	@Override
	public List<License> readAllLicenses() {
		return service.readAllLicenses();
	}

	@Override
	public License createLicense(License License) {
		return service.createLicense(License);
	}

	@Override
	public License updateLicense(License License) {
		return service.updateLicense(License);
	}

	@Override
	public void deleteLicense(long id) {
		service.deleteLicense(id);
	}

	@Override
	public List<License> findLicenses(String login, String aircraftType) {
		return service.findLicenses(login, aircraftType);
	}
}
