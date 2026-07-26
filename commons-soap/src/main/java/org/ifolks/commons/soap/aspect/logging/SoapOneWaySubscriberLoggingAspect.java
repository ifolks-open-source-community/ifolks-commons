package org.ifolks.commons.soap.aspect.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.aspects.LoggingAspectTemplate;
import org.ifolks.commons.soap.annotations.SoapOneWaySubscriber;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class SoapOneWaySubscriberLoggingAspect extends LoggingAspectTemplate {

	@Override
	@Pointcut("@annotation(org.ifolks.commons.soap.annotations.SoapOneWaySubscriber)")
	protected void onPointcut() {}
	
	@Override
	protected Object getResponseBody(Object proceed) {
		return null;
	}
	
	@Override
	protected String getFallbackTransactionType(Method proxiedMethod) {
		return proxiedMethod.getAnnotation(SoapOneWaySubscriber.class).value();
	}
}
