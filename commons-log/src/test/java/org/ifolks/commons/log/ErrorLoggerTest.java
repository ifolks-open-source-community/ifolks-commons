package org.ifolks.commons.log;

import org.ifolks.commons.api.exception.TechnicalError;
import org.ifolks.commons.text.serialization.JsonSerializer;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorLoggerTest {

	private static ErrorLogger logger;

	
	@BeforeClass
	public static void setUpBeforeClass() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		logger = new ErrorLogger(new JsonSerializer(objectMapper));
	}
	
	
	@Test
	public void test() {		
		logger.logApplicationException(new TechnicalError("error", new TechnicalError("root cause")));
	}
}
