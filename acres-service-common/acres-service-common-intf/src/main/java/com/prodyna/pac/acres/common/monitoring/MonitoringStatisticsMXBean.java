package com.prodyna.pac.acres.common.monitoring;

import java.util.List;

public interface MonitoringStatisticsMXBean {
	
	List<String> showExecutedMethodNames();

	void addMethodExecution(String methodName, Long duration);

	List<Long> getMethodDurations(String methodName);

	Long getAverageMethodDuration(String methodName);

	int getMethodExecutionCount(String methodName);

	void resetMethodStatistics(String methodName);

	void resetAllMethodStatistics();
}
