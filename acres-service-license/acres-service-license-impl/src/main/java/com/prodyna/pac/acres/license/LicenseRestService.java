package com.prodyna.pac.acres.license;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("license")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface LicenseRestService extends LicenseService {

	@GET
	@RolesAllowed("admin")
	@Override
	List<License> readAllLicenses();

	@GET
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	@Override
	License readLicense(@PathParam("id") long id);

	@POST
	@RolesAllowed("admin")
	@Override
	License createLicense(License License);

	@PUT
	@RolesAllowed("admin")
	@Override
	License updateLicense(License License);

	@DELETE
	@Path("{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	@Override
	void deleteLicense(@PathParam("id") long id);

	@GET
	@Path("search")
	@RolesAllowed("admin")
	@Override
	List<License> findLicenses(@QueryParam("login") String login, @QueryParam("acType") String aircraftType);
}
