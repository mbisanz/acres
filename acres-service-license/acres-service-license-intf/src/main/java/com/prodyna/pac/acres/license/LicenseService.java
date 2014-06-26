package com.prodyna.pac.acres.license;

import java.util.List;

public interface LicenseService {

	List<License> readAllLicenses();

	License readLicense(long id);

	License createLicense(License license);

	License updateLicense(License license);

	void deleteLicense(long id);

	List<License> findLicenses(String login, String aircraftType);
}
