package org.ifolks.commons.log.logger;

import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.log.data.AccessLogMessage;
import org.ifolks.commons.log.data.InterfaceCallLogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.databind.json.JsonMapper;

public class AccessLogger {
	
	private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOG");
	private static final Logger classLogger = LoggerFactory.getLogger(AccessLogger.class);
	
	private JsonMapper jsonMapper;
	
	public AccessLogger() {
		this.jsonMapper = JsonMapperFactory.createDefaultJsonMapper();
	}
	
	public AccessLogger(JsonMapper jsonMapper) {
		super();
		this.jsonMapper = jsonMapper;
	}
	
	
	/**
	 * Used to log a request received as a backend
	 */
	public void logRequest(String transactionType, Object requestBody) {
		try {
			String serialized = jsonMapper.writeValueAsString(AccessLogMessage.request(transactionType, requestBody));
			logger.info(serialized);
		} catch (Exception e) {
			classLogger.error("failed to log request : " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * Used to log a response sent as a backend
	 */
	public void logResponse(String transactionType, Object responseBody, Long responseTimeMillis, String responseStatus, String responseLabel) {
		try {
			String serialized = jsonMapper.writeValueAsString(AccessLogMessage.response(transactionType, responseBody, responseTimeMillis, responseStatus, responseLabel));
			logger.info(serialized);
		} catch (Exception e) {
			classLogger.error("failed to log response : " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * Used to log a request sent as a client
	 */
	public void logInterfaceCall(String interfaceName, RequestChannels interfaceChannel, Object sentBody) {
		try {
			String serialized = jsonMapper.writeValueAsString(InterfaceCallLogMessage.call(interfaceName, interfaceChannel, sentBody));
			logger.info(serialized);
		} catch (Exception e) {
			classLogger.error("failed to log interface call : " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * Used to log a response received as a client
	 */
	public void logInterfaceAnswer(String interfaceName, RequestChannels interfaceChannel, Object receivedBody, Long responseTimeMillis, String responseStatus, String responseLabel) {
		try {
			String serialized = jsonMapper.writeValueAsString(InterfaceCallLogMessage.answer(interfaceName, interfaceChannel, receivedBody, responseTimeMillis, responseStatus, responseLabel));
			logger.info(serialized);
		} catch (Exception e) {
			classLogger.error("failed to log interface answer : " + e.getMessage(), e);
		}
	}
}
