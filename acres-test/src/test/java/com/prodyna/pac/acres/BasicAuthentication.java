package com.prodyna.pac.acres;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.xml.bind.DatatypeConverter;

public class BasicAuthentication implements ClientRequestFilter {

	private String auth;

	public BasicAuthentication(String username, String password) throws UnsupportedEncodingException {
		String userPasswd = String.format("%s:%s", username, password);
		String base64UserPasswd = DatatypeConverter.printBase64Binary(userPasswd.getBytes("UTF-8"));
		auth = "Basic " + base64UserPasswd;
	}

	@Override
	public void filter(ClientRequestContext clientRequestContext) throws IOException {
		clientRequestContext.getHeaders().add("Authorization", auth);
	}
}
