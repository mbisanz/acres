package com.prodyna.pac.acres;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;

import com.prodyna.pac.acres.common.qualifier.Unsecured;
import com.prodyna.pac.acres.showcase.ShowcaseService;
import com.prodyna.pac.acres.user.context.LoginConfiguration;

public class AbstractAcresTest {

	@Inject
	@Unsecured
	protected ShowcaseService showcaseService;

	@Inject
	protected LoginConfiguration testUser;

	@Before
	public void setUp() throws Exception {
		showcaseService.resetDatabase();
		showcaseService.createShowcase();
		// run all tests in context of pilot1 user
		testUser.setLogin("pilot1");
	}

	@After
	public void tearDown() throws Exception {
		testUser.setLogin(null);
	}
}