package com.prodyna.pac.acres;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class AbstractAcresRestTest {

	@ArquillianResource
	private URL url;

	protected Client createClient(String user, String password) {
		final Client client = ClientBuilder.newClient();
		if (user != null && password != null) {
			client.register(new AuthenticationRequestFilter(user, password));
		}
		return client;
	}

	protected WebTarget createWebTarget(String user, String password) {
		String fullPath = url.toString() + "rest";
		Client client = createClient(user, password);
		WebTarget target = client.target(fullPath);
		return target;
	}

	protected <C> C createService(Class<C> ifaceType, String user, String password) {
		WebTarget target = createWebTarget(user, password);
		C proxy = ((ResteasyWebTarget) target).proxy(ifaceType);
		return proxy;
	}
}