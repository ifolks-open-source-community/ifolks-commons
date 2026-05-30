package org.ifolks.commons.rest.client.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.ErrorReport;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.jackson.databind.json.JsonMapper;

public class ErrorReportHandlerTest {

	private static JsonMapper jsonMapper = JsonMapper.builder().build();
	private static ErrorReportHandler errorReportHandler;

	
	@BeforeClass
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
			Assert.assertEquals(e.getMessage(), message);
			Assert.assertTrue(e instanceof TestException);
			
			return;
		}
		Assert.fail();		
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
			Assert.assertEquals(e.getMessage(), message);
			Assert.assertTrue(e instanceof TestException);
 
			return;
		}
		Assert.fail();
	}
}
