package org.ifolks.commons.rest.client.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.ErrorReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.json.JsonMapper;

public class ErrorReportHandlerTest {

	private static JsonMapper jsonMapper = JsonMapper.builder().build();
	private static ErrorReportHandler errorReportHandler;

	
	@BeforeAll
	public static void setUpBeforeClass() {
		errorReportHandler = new ErrorReportHandler();
		errorReportHandler.setJsonMapper(jsonMapper);
	}
	
	
	@Test
	public void testApplicationExceptionNoDetails() {
		
		String message = "test";
		
		ErrorReport errorReport = new ErrorReport();
		errorReport.setExceptionClassName(TestException.class.getName());
		errorReport.setMessage(message);
		
		try {
			errorReportHandler.convertErrorReport(errorReport);
		} catch (ApplicationException e) {
			Assertions.assertEquals(e.getMessage(), message);
			Assertions.assertTrue(e instanceof TestException);
			
			return;
		}
		Assertions.fail();		
	}
	
	
	@Test
	public void testApplicationExceptionDummyDetails() {
		
		String message = "test";
		Dummy dummy = new Dummy(1L, "test");
		
		ErrorReport errorReport = new ErrorReport();
		errorReport.setExceptionClassName(TestException.class.getName());
		errorReport.setMessage(message);
		
		try {
			errorReportHandler.convertErrorReport(errorReport);
		} catch (ApplicationException e) {
			Assertions.assertEquals(e.getMessage(), message);
			Assertions.assertTrue(e instanceof TestException);
 
			return;
		}
		Assertions.fail();
	}
}


