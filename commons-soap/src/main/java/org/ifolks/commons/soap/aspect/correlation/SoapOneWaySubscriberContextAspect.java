package org.ifolks.commons.soap.aspect.correlation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.aspects.RequestContextAspectTemplate;
import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.log.context.RequestContext;
import org.springframework.core.annotation.Order;

/**
 * An aspect to put the transaction id and correlation id in the {@link RequestContext}. This will be useful for logs tracking.
 * 
 * @author Nicolas Thibault
 *
 */
@Aspect
@Order(0)
public class SoapOneWaySubscriberContextAspect extends RequestContextAspectTemplate {

	public SoapOneWaySubscriberContextAspect() {
		super(RequestChannels.JMS_SOAP);
	}
	

	@Override
	@Pointcut("@annotation(org.ifolks.commons.soap.annotations.SoapOneWaySubscriber)")
	protected void onPointcut() {}
	

	@Override
	protected String getCorrelationId(JoinPoint joinPoint) {
		return null;
	}
}
