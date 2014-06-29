package com.prodyna.pac.acres.showcase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Unsecured;

@RequestScoped
@Logged
public class ShowcaseRestServiceResource implements ShowcaseRestService {

	@Inject
	@Unsecured
	private ShowcaseService service;

	@Override
	public void createShowcase() {
		service.resetDatabase();
		service.createShowcase();
	}
}