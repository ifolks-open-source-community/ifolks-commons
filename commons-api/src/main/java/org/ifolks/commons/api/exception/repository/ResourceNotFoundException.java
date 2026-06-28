package org.ifolks.commons.api.exception.repository;

import org.ifolks.commons.api.exception.ApplicationException;

/**
 * Exception thrown when a resource requested by an API endpoint is not found.
 * <br>Maps to HTTP 404 (Not Found).
 * 
 * @author Antigravity
 *
 */
public class ResourceNotFoundException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getHttpErrorCode() {
		return "404";
	}
}
