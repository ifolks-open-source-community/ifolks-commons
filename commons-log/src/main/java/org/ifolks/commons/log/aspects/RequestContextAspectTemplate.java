package org.ifolks.commons.log.aspects;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.log.context.RequestContext;
import org.ifolks.commons.log.context.RequestContextHolder;
import org.ifolks.commons.text.StringUtils;

public abstract class RequestContextAspectTemplate {
	
	private RequestChannels channel;
	
	public RequestContextAspectTemplate(RequestChannels channel) {
		super();
		this.channel = channel;
	}
	

	@Around("onPointcut()")
	public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String transactionId = UUID.randomUUID().toString();
		
		String correlationId = getCorrelationId(joinPoint);
		if (StringUtils.isEmpty(correlationId)) {
			correlationId = transactionId;
		}
		
		RequestContextHolder.bind(new RequestContext(transactionId, correlationId, channel));

		try {			
			return joinPoint.proceed();	
		} finally {
			RequestContextHolder.unbind();
		}
	}
	
	protected abstract void onPointcut();

	protected abstract String getCorrelationId(JoinPoint joinPoint);
}
