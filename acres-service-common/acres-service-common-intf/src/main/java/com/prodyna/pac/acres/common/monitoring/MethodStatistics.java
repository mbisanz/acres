package com.prodyna.pac.acres.common.monitoring;

import java.util.ArrayList;
import java.util.List;

public class MethodStatistics {

	private String methodName;
	private List<Long> methodExecutionMillis = new ArrayList<>();

	public MethodStatistics() {
	}

	public MethodStatistics(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Long> getMethodExecutionMillis() {
		return methodExecutionMillis;
	}

	public void setMethodExecutionMillis(List<Long> methodExecutionsMillis) {
		this.methodExecutionMillis = methodExecutionsMillis;
	}

	public Double getAverageMethodExecutionMillis() {
		if (methodExecutionMillis.isEmpty()) {
			return null;
		}
		Double result = 0.0;
		for (Long val : methodExecutionMillis) {
			result += val;
		}
		return result / methodExecutionMillis.size();
	}
}
