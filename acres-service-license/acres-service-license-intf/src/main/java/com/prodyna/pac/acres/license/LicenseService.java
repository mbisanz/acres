package com.prodyna.pac.acres.license;

import java.util.List;

import javax.validation.Valid;

public interface LicenseService {

	List<License> readAllLicenses();

	License readLicense(long id);

	License createLicense(@Valid License license);

	License updateLicense(@Valid License license);

	void deleteLicense(long id);

	List<License> findLicenses(String login, String aircraftType);
}
