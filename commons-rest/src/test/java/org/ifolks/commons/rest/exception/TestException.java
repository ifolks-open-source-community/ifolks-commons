package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;

public class TestException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public TestException() {
	}

	public TestException(String message) {
		super(message);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}
}
