package com.prodyna.pac.acres.common.monitoring;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

@Singleton
@Startup
public class MonitoringStatisticsBean implements MonitoringStatisticsMXBean {

	private static final int DEFAULT_MAX_SAMPLES_PER_METHOD = 1000;

	private MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

	private ObjectName objectName = null;

	private int maxSamplesPerMethod = DEFAULT_MAX_SAMPLES_PER_METHOD;

	private final Map<String, MethodStatistics> statisticsMap = new HashMap<String, MethodStatistics>();

	@PostConstruct
	public void registerInJMX() {

		try {
			objectName = new ObjectName(this.getClass().getPackage().getName() + ":type="
					+ this.getClass().getSimpleName());
			mBeanServer.registerMBean(this, objectName);
		} catch (JMException e) {
			throw new RuntimeException("Could not register statistics monitoring as JMX MBean", e);
		}
	}

	@PreDestroy
	public void unregisterFromJMX() {
		try {
			mBeanServer.unregisterMBean(objectName);
		} catch (JMException e) {
			throw new RuntimeException("Could not unregister statistics monitoring as JMX MBean", e);
		}
	}

	public int getMaxSamplesPerMethod() {
		return maxSamplesPerMethod;
	}

	public void setMaxSamplesPerMethod(int maxSamplesPerMethod) {
		this.maxSamplesPerMethod = maxSamplesPerMethod;
	}

	@Override
	public synchronized void addMethodExecution(String methodName, Long duration) {
		MethodStatistics methodStatistics = statisticsMap.get(methodName);
		if (methodStatistics == null) {
			methodStatistics = new MethodStatistics(methodName);
			statisticsMap.put(methodName, methodStatistics);
		}
		int size = methodStatistics.getMethodExecutionMillis().size();
		if (size >= maxSamplesPerMethod) {
			methodStatistics.getMethodExecutionMillis().remove(size - 1);
		}
		methodStatistics.getMethodExecutionMillis().add(duration);
	}

	public Map<String, MethodStatistics> getMethodStatistics() {
		return statisticsMap;
	}

	@Override
	public synchronized void resetMethodStatistics(String methodName) {
		statisticsMap.remove(methodName);
	}

	@Override
	public synchronized void resetAllMethodStatistics() {
		statisticsMap.clear();
	}
}
