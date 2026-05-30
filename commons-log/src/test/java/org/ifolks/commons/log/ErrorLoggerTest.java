package org.ifolks.commons.log;

import org.ifolks.commons.api.exception.TechnicalError;
import org.ifolks.commons.text.serialization.JsonSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import tools.jackson.databind.json.JsonMapper;

public class ErrorLoggerTest {

	private static ErrorLogger logger;

	
	@BeforeAll
	public static void setUpBeforeClass() {
		JsonMapper jsonMapper = JsonMapper.builder()
			.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(Include.NON_NULL))
			.build();
		logger = new ErrorLogger(new JsonSerializer(jsonMapper));
	}
	
	
	@Test
	public void test() {		
		logger.logApplicationException(new TechnicalError("error", new TechnicalError("root cause")));
	}
}


