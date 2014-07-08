package com.prodyna.pac.acres.user.context;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginConfiguration {

	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
