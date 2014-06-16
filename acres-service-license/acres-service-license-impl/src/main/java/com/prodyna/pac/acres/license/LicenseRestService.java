package com.prodyna.pac.acres.license;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("license")
@Produces("application/json")
@Consumes("application/json")
public interface LicenseRestService extends LicenseService {

	@GET
	@Override
	List<License> readAllLicenses();

	@GET
	@Path("{id}")
	@Override
	License readLicense(@PathParam("id") long id);

	@POST
	@Override
	License createLicense(License License);

	@PUT
	@Override
	License updateLicense(License License);

	@DELETE
	@Path("{id}")
	@Override
	void deleteLicense(@PathParam("id") long id);
}
