package org.ifolks.commons.jms.aspect.correlation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.aspects.RequestContextAspectTemplate;
import org.ifolks.commons.log.context.RequestChannels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import jakarta.jms.Message;

/**
 * An aspect to put the request id in a ThreadLocal. This will be useful for logs tracking.
 * 
 * @author Nicolas Thibault
 *
 */
@Aspect
@Order(0)
public class JmsContextAspect extends RequestContextAspectTemplate {
	
	private static final Logger logger = LoggerFactory.getLogger(JmsContextAspect.class);
	
	public JmsContextAspect() {
		super(RequestChannels.JMS);
	}	

	@Override
	@Pointcut("@annotation(org.springframework.jms.annotation.JmsListener)")
	protected void onPointcut(){}
	
	
	@Override
	protected String getCorrelationId(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		Message message;
		
		try {
			for (Object arg:args) {
				if (arg instanceof Message) {
					message = (Message)arg;
					return message.getJMSCorrelationID();
				}
			}
			logger.warn("Could not get Correlation ID due to missing message argument");
			return null;
		} catch (Exception e) {
			logger.error("Could not get Correlation ID : " + e.getMessage(), e);
			return null;
		}
	}
}
