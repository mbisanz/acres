package com.prodyna.pac.acres.common.monitoring;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

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

	private final Map<String, Queue<Long>> statisticsMap = new HashMap<String, Queue<Long>>();

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

	@Override
	public synchronized List<String> showExecutedMethodNames() {
		return new ArrayList<>(statisticsMap.keySet());
	}

	@Override
	public synchronized void addMethodExecution(String methodName, Long duration) {
		Queue<Long> methodValues = getInitMethodValues(methodName, true);
		if (!methodValues.offer(duration)) {
			methodValues.poll();
			methodValues.offer(duration);
		}
	}

	@Override
	public synchronized List<Long> getMethodDurations(String methodName) {
		Queue<Long> methodValues = getInitMethodValues(methodName);
		return new ArrayList<>(methodValues);
	}

	@Override
	public synchronized Long getAverageMethodDuration(String methodName) {
		Queue<Long> methodValues = getInitMethodValues(methodName);
		if (methodValues.isEmpty()) {
			return null;
		}
		Long result = 0L;
		for (Long val : methodValues) {
			result += val;
		}
		return result / methodValues.size();
	}

	@Override
	public synchronized int getMethodExecutionCount(String methodName) {
		Queue<Long> methodValues = getInitMethodValues(methodName);
		return methodValues.size();
	}

	@Override
	public synchronized void resetMethodStatistics(String methodName) {
		statisticsMap.remove(methodName);
	}

	@Override
	public synchronized void resetAllMethodStatistics() {
		statisticsMap.clear();
	}

	private Queue<Long> getInitMethodValues(String methodName) {
		return getInitMethodValues(methodName, false);
	}

	private Queue<Long> getInitMethodValues(String methodName, boolean addIfNull) {
		Queue<Long> methodValues = statisticsMap.get(methodName);
		if (methodValues == null) {
			methodValues = new LinkedBlockingQueue<>(maxSamplesPerMethod);
			if (addIfNull) {
				statisticsMap.put(methodName, methodValues);
			}
		}
		return methodValues;
	}

}
