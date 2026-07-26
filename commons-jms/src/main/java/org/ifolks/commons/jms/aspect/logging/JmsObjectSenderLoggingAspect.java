package org.ifolks.commons.jms.aspect.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.jms.annotations.JmsObjectSender;
import org.ifolks.commons.log.aspects.OneWayPublisherLoggingInterceptorTemplate;
import org.ifolks.commons.log.context.RequestChannels;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class JmsObjectSenderLoggingAspect extends OneWayPublisherLoggingInterceptorTemplate {

	public JmsObjectSenderLoggingAspect() {
		super(RequestChannels.JMS);
	}

	@Override
	@Pointcut("@annotation(org.ifolks.commons.jms.annotations.JmsObjectSender)")
	protected void onPointcut() {}

	@Override
	protected String getFallbackTransactionType(Method proxiedMethod) {
		return proxiedMethod.getAnnotation(JmsObjectSender.class).destination();
	}
}
