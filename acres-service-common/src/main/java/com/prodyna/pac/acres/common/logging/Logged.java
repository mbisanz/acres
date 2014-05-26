package com.prodyna.pac.acres.common.logging;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Annotation for marking services for the {@link LoggedInterceptor}.
 * 
 * @author Martin Bisanz, PRODYNA AG
 */
@InterceptorBinding
@Inherited
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
public @interface Logged {
}
