package org.ifolks.commons.soap.aspect.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ifolks.commons.log.AccessLogger;
import org.ifolks.commons.log.aspects.TwoWaysConsumerLoggingInterceptorTemplate;
import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.soap.annotations.SoapTwoWaysConsumer;
import org.ifolks.commons.text.serialization.XmlSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class SoapTwoWaysConsumerLoggingAspect extends TwoWaysConsumerLoggingInterceptorTemplate {

	public SoapTwoWaysConsumerLoggingAspect() {
		super(RequestChannels.HTTP_SOAP);
	}


	private static final Logger logger = LoggerFactory.getLogger(AccessLogger.class);
	private XmlSerializer serializer = new XmlSerializer();

	
	@Override
	@Pointcut("@annotation(org.ifolks.commons.soap.annotations.SoapTwoWaysConsumer)")
	protected void onPointcut() {}
	
	
	@Override
	protected Object getSentBody(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		
		try {
			return serializer.serialize(args[0]);
		} catch (Exception e) {
			logger.error("could not serialize request body : " + e.getMessage(), e);
			return null;
		}
	}
	
	
	@Override
	protected Object getReceivedBody(Object proceed) {
		try {
			return serializer.serialize(proceed);
		} catch (Exception e) {
			logger.error("could not serialize response body : " + e.getMessage(), e);
			return null;
		}
	}
	
	
	@Override
	protected String getFallbackTransactionType(Method proxiedMethod) {
		return proxiedMethod.getAnnotation(SoapTwoWaysConsumer.class).value();
	}
}
