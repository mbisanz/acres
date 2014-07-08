package com.prodyna.pac.acres;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.resteasy.util.Base64;

public class AuthenticationRequestFilter implements ClientRequestFilter {

	private final String username;
	private final String password;

	public AuthenticationRequestFilter(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		String token = username + ":" + password;
		String base64Token = Base64.encodeBytes(token.getBytes(StandardCharsets.UTF_8));
		requestContext.getHeaders().add("Authorization", "Basic " + base64Token);
	}
}