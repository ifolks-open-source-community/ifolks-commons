package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.ErrorReport;
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
		
		ErrorReport errorReport = restExceptionHandler.handleApplicationException(e);
		
		Assertions.assertEquals(errorReport.getMessage(), message);
		Assertions.assertTrue(errorReport.getExceptionClassName().equals(TestException.class.getName()));
	}
}


