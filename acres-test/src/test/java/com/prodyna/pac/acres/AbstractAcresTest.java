package com.prodyna.pac.acres;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractAcresTest {

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "acres-test.war");
		wa.addPackages(true, "com.prodyna.pac.acres");
		wa.addAsResource("META-INF/persistence.xml");
		wa.addAsWebInfResource("beans.xml");
		System.out.println(wa.toString(true));
		return wa;
	}

	protected void login(final String username, final String password) throws Exception {
		SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
		securityClient.setSimple(username, password);
		securityClient.login();
	}

	protected void logout() throws Exception {
		SecurityClientFactory.getSecurityClient().logout();
	}
}
