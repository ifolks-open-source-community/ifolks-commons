package org.ifolks.commons.soap.aspect.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.aspects.TwoWaysConsumerLoggingInterceptorTemplate;
import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.soap.annotations.SoapTwoWaysConsumer;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class SoapTwoWaysConsumerLoggingAspect extends TwoWaysConsumerLoggingInterceptorTemplate {

	public SoapTwoWaysConsumerLoggingAspect() {
		super(RequestChannels.HTTP_SOAP);
	}

	@Override
	@Pointcut("@annotation(org.ifolks.commons.soap.annotations.SoapTwoWaysConsumer)")
	protected void onPointcut() {}
	
	@Override
	protected String getFallbackTransactionType(Method proxiedMethod) {
		return proxiedMethod.getAnnotation(SoapTwoWaysConsumer.class).value();
	}
}
