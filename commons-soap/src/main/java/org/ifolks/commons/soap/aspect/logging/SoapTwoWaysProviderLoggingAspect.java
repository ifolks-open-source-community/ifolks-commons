package org.ifolks.commons.soap.aspect.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.aspects.LoggingAspectTemplate;
import org.ifolks.commons.soap.annotations.SoapTwoWaysProvider;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class SoapTwoWaysProviderLoggingAspect extends LoggingAspectTemplate {

	@Override
	@Pointcut("@annotation(org.ifolks.commons.soap.annotations.SoapTwoWaysProvider)")
	protected void onPointcut() {}
	
	@Override
	protected String getFallbackTransactionType(Method proxiedMethod) {
		return proxiedMethod.getAnnotation(SoapTwoWaysProvider.class).value();
	}
}
