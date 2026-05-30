package org.ifolks.commons.log;

import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.text.serialization.JsonSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import tools.jackson.databind.json.JsonMapper;

public class AccessLoggerTest {

	private static AccessLogger logger;

	
	@BeforeAll
	public static void setUpBeforeClass() {
		JsonMapper jsonMapper = JsonMapper.builder()
			.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(Include.NON_NULL))
			.build();
		logger = new AccessLogger(new JsonSerializer(jsonMapper));
	}
	
	
	@Test
	public void test() {
		
		logger.logRequest("MY_SERVICE", new Dummy(1L,"dummy request"));
		
		logger.logInterfaceCall("EXTERNAL_SERVICE", RequestChannels.HTTP_REST, new Dummy(2L,"dummy call"));
		
		logger.logInterfaceAnswer("EXTERNAL_SERVICE", RequestChannels.HTTP_REST, new Dummy(2L,"dummy callback"), 5L, "200", "OK");
		
		logger.logResponse("MY_SERVICE", new Dummy(4L,"dummy response"), 10L, "200", "OK");
	}
}


