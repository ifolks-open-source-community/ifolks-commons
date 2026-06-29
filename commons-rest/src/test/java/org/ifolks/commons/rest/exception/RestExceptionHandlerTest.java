package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.springframework.http.ProblemDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RestExceptionHandlerTest {

	private static RestExceptionHandler restExceptionHandler;

	
	@BeforeAll
	public static void setUpBeforeClass() {
		restExceptionHandler = new RestExceptionHandler();
	}
	
	
	@Test
	public void testApplicationExceptionNoDetails() {
		
		String message = "test";
		ApplicationException e = new TestException(message);
		
		ProblemDetail problemDetail = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(problemDetail.getDetail(), message);
		Assertions.assertTrue(TestException.class.getName().equals(problemDetail.getProperties().get("exception")));
	}
}
