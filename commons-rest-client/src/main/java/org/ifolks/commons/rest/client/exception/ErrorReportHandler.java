package org.ifolks.commons.rest.client.exception;

import java.io.IOException;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.ErrorReport;
import org.ifolks.commons.api.exception.TechnicalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import tools.jackson.databind.json.JsonMapper;


/**
 * this class is responsible for handling error reports that a rest service can respond.<br/>
 * it works as a convertor from a  {@link ErrorReport} to a {@link ApplicationException}
 * 
 * @author Nicolas Thibault
 *
 */
public class ErrorReportHandler implements ResponseErrorHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorReportHandler.class);
	
	private JsonMapper jsonMapper;
	
	public JsonMapper getJsonMapper() {
		return jsonMapper;
	}
	public void setJsonMapper(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}


	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.client.ResponseErrorHandler#hasError(org.springframework.http.client.ClientHttpResponse)
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !response.getStatusCode().equals(HttpStatus.OK);
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.client.ResponseErrorHandler#handleError(java.net.URI, org.springframework.http.HttpMethod, org.springframework.http.client.ClientHttpResponse)
	 */
	@Override
	public void handleError(java.net.URI url, org.springframework.http.HttpMethod method, ClientHttpResponse response) throws IOException {

		try {
			ErrorReport errorReport = jsonMapper.readValue(response.getBody(), ErrorReport.class);
			convertErrorReport(errorReport);
		} catch (IOException e) {
			logger.warn("Could not read error report : a TechnicalError will be thrown");
			throw new TechnicalError(TechnicalError.ERROR_UNKNOWN, e);
		}
	}

	
	/**
	 * instantiates the {@link ApplicationException} as described in the {@link ErrorReport} that a rest service can respond
	 * @param errorReport
	 */
	public void convertErrorReport(ErrorReport errorReport) {
		
		ApplicationException exception;
		
		try {
			exception = (ApplicationException) Class.forName(errorReport.getExceptionClassName()).getDeclaredConstructor().newInstance();
			exception.setMessage(errorReport.getMessage());
			
		} catch (Exception e) {
			logger.warn("Could not instantiate exception from rest response : a TechnicalError will be used instead");
			exception = new TechnicalError(TechnicalError.ERROR_UNKNOWN, e);
		}
		throw exception;
	}
}