package com.prodyna.pac.acres;

import javax.inject.Inject;

import org.junit.Before;

import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.showcase.ShowcaseService;

public class AbstractAcresTest {

	@Inject
	@Unsecured
	protected ShowcaseService showcaseService;

	@Before
	public void setUp() throws Exception {
		showcaseService.resetDatabase();
		showcaseService.createShowcase();
	}
}