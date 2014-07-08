package com.prodyna.pac.acres.common.monitoring;

import java.util.Map;

public interface MonitoringStatisticsMXBean {

	void addMethodExecution(String methodName, Long duration);

	Map<String, MethodStatistics> getMethodStatistics();

	void resetMethodStatistics(String methodName);

	void resetAllMethodStatistics();
}
