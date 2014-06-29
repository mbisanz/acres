package com.prodyna.pac.acres.common.monitoring;

import java.lang.management.ManagementFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.management.MBeanServer;

@ApplicationScoped
public class MBeanServerProducer {

	@Produces
	public MBeanServer produceMBeanServer() {
		return ManagementFactory.getPlatformMBeanServer();
	}
}
