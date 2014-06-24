package com.prodyna.pac.acres.common.monitoring;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Monitored
@Dependent
@Interceptor
public class MonitoredInterceptor {

	@Inject
	private MonitoringStatisticsMXBean monitoringStatisticsMXBean;

	@AroundInvoke
	public Object monitorPerformance(final InvocationContext ctx) throws Exception {
		long start = System.currentTimeMillis();
		Object result = ctx.proceed();
		long end = System.currentTimeMillis();
		String name = ctx.getTarget().getClass().getName() + ":" + ctx.getMethod().getName();
		monitoringStatisticsMXBean.addMethodExecution(name, end - start);
		return result;
	}
}
