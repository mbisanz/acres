package com.prodyna.pac.acres.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.interceptors.RoleBasedSecurityFeature;

@ApplicationPath("/rest")
public class AcresApplication extends Application {

	public AcresApplication(@Context Dispatcher dispatcher) {
		dispatcher.getProviderFactory().register(RoleBasedSecurityFeature.class);
	}
}
