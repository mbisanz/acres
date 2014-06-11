package com.prodyna.pac.acres.common.logging;

import java.lang.reflect.Method;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

/**
 * CDI interceptor which logs all method invocations of annotated services.
 * 
 * @author Martin Bisanz, PRODYNA AG
 */
@Logged
@Dependent
@Interceptor
public class LoggedInterceptor {

	/**
	 * Logger instance.
	 */
	@Inject
	private Logger log;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext invocationContext) throws Exception {
		long start = System.currentTimeMillis();
		Object proceed = invocationContext.proceed();
		if (log.isTraceEnabled()) {
			long time = System.currentTimeMillis() - start;
			Method method = invocationContext.getMethod();
			log.trace("{}:{}({}) [{}ms]: {}", method.getDeclaringClass().getName(), method.getName(),
					getParameters(invocationContext), time, proceed);
		}
		return proceed;
	}

	private String getParameters(InvocationContext invocationContext) {
		StringBuilder builder = new StringBuilder();
		for (Object param : invocationContext.getParameters()) {
			if (builder.length() > 0) {
				builder.append(",");
			}
			builder.append(param == null ? "null" : param.toString());
		}
		builder.append(")");
		return builder.toString();
	}
}
