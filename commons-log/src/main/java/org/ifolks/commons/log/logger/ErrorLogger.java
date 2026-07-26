package org.ifolks.commons.log.logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.log.data.ErrorLogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.databind.json.JsonMapper;

public class ErrorLogger {
	
	private static final Logger logger = LoggerFactory.getLogger("ERROR_LOG");
	private static final Logger classLogger = LoggerFactory.getLogger(ErrorLogger.class);
	
	private JsonMapper jsonMapper;	
	private boolean printErrorStackInRootLogger = true;
	

	public void setPrintErrorStackInRootLogger(boolean printErrorStackInRootLogger) {
		this.printErrorStackInRootLogger = printErrorStackInRootLogger;
	}

	public void setJsonMapper(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}
	
	
	/*
	 * constructors
	 */
	public ErrorLogger() {
		this.jsonMapper = JsonMapperFactory.createDefaultJsonMapper();
	}
	
	public ErrorLogger(JsonMapper jsonMapper) {
		this(jsonMapper, true);
	}
	
	public ErrorLogger(JsonMapper jsonMapper, boolean printErrorStackInRootLogger) {
		super();
		this.jsonMapper = jsonMapper;
		this.printErrorStackInRootLogger = printErrorStackInRootLogger;
	}
	
	/**
	 * Used to log an error
	 */
	public void logApplicationException(ApplicationException e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		ErrorLogMessage errorLogMessage = new ErrorLogMessage(e.getHttpErrorCode(), e.getMessage(), stringWriter.toString());
		
		try {
			String serialized = jsonMapper.writeValueAsString(errorLogMessage);
			logger.error(serialized);
			if (printErrorStackInRootLogger) classLogger.error(e.getMessage(), e);
		} catch (Exception ex) {
			classLogger.error("failed to log application exception : " + ex.getMessage(), ex);
		}
	}
	
	
	public void logException(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		ErrorLogMessage errorLogMessage = new ErrorLogMessage("500", e.getMessage(), stringWriter.toString());
		
		try {
			String serialized = jsonMapper.writeValueAsString(errorLogMessage);
			logger.error(serialized);
			if (printErrorStackInRootLogger) classLogger.error(e.getMessage(), e);
		} catch (Exception ex) {
			classLogger.error("failed to log exception : " + ex.getMessage(), ex);
		}
	}

	public void logException(Exception e, String status, String label) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		ErrorLogMessage errorLogMessage = new ErrorLogMessage(status, label, stringWriter.toString());
		
		try {
			String serialized = jsonMapper.writeValueAsString(errorLogMessage);
			logger.error(serialized);
			if (printErrorStackInRootLogger) classLogger.error(label, e);
		} catch (Exception ex) {
			classLogger.error("failed to log exception : " + ex.getMessage(), ex);
		}
	}
}
