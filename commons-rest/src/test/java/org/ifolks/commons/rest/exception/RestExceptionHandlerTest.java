package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.ErrorReport;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RestExceptionHandlerTest {

	private static RestExceptionHandler restExceptionHandler;

	
	@BeforeClass
	public static void setUpBeforeClass() {
		restExceptionHandler = new RestExceptionHandler();
	}
	
	
	@Test
	public void testApplicationExceptionNoDetails() {
		
		String message = "test";
		ApplicationException e = new TestException(message);
		
		ErrorReport errorReport = restExceptionHandler.handleApplicationException(e);
		
		Assert.assertEquals(errorReport.getMessage(), message);
		Assert.assertTrue(errorReport.getExceptionClassName().equals(TestException.class.getName()));
	}
}
