package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.repository.ObjectNotFoundException;
import org.ifolks.commons.api.exception.repository.ResourceNotFoundException;
import org.ifolks.commons.api.exception.state.InvalidStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;

public class RestExceptionHandlerTest {

	private static RestExceptionHandler restExceptionHandler;

	@BeforeAll
	public static void setUpBeforeClass() {
		restExceptionHandler = new RestExceptionHandler();
		restExceptionHandler.setPrintErrorStackInRootLogger(false);
	}

	@Test
	public void testApplicationExceptionNoDetails() {
		String message = "test";
		ApplicationException e = new TestException(message);
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(problemDetail.getDetail(), message);
		Assertions.assertEquals(TestException.class.getName(), problemDetail.getProperties().get("exception"));
	}

	@Test
	public void testObjectNotFoundException() {
		String message = "object not found";
		ObjectNotFoundException e = new ObjectNotFoundException(message);
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
		Assertions.assertEquals(message, problemDetail.getDetail());
		Assertions.assertEquals("404", e.getHttpErrorCode());
	}

	@Test
	public void testResourceNotFoundException() {
		String message = "resource not found";
		ResourceNotFoundException e = new ResourceNotFoundException(message);
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
		Assertions.assertEquals(message, problemDetail.getDetail());
		Assertions.assertEquals("404", e.getHttpErrorCode());
	}

	@Test
	public void testInvalidStateException() {
		String message = "invalid state";
		InvalidStateException e = new InvalidStateException(message);
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
		Assertions.assertEquals(message, problemDetail.getDetail());
		Assertions.assertEquals("409", e.getHttpErrorCode());
	}

	@Test
	public void testAccessDeniedException() {
		AccessDeniedException e = new AccessDeniedException("Access is denied");
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), problemDetail.getStatus());
		Assertions.assertEquals("access.denied", problemDetail.getDetail());
	}
}
