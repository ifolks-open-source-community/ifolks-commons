package org.ifolks.commons.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.ifolks.commons.log.context.RequestChannels;
import org.ifolks.commons.log.logger.AccessLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

public class AccessLoggerTest {

	private AccessLogger accessLogger;
	private ListAppender<ILoggingEvent> listAppender;
	private JsonMapper jsonMapper = JsonMapper.builder().build();

	@BeforeEach
	public void setUp() {
		accessLogger = new AccessLogger();
		
		Logger logger = (Logger) LoggerFactory.getLogger("ACCESS_LOG");
		listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
	}

	@Test
	public void testLogRequest() throws Exception {
		accessLogger.logRequest("MY_SERVICE", new Dummy(1L, "dummy request"));

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(1, logs.size());
		
		String logJson = logs.get(0).getFormattedMessage();
		JsonNode node = jsonMapper.readTree(logJson);
		
		assertEquals("REQUEST", node.get("transactionStage").asString());
		assertEquals("MY_SERVICE", node.get("transactionType").asString());
		assertEquals(1, node.get("requestBody").get("longField").asInt());
		assertEquals("dummy request", node.get("requestBody").get("stringField").asString());
	}

	@Test
	public void testLogInterfaceCallAndAnswer() throws Exception {
		accessLogger.logInterfaceCall("EXTERNAL_SERVICE", RequestChannels.HTTP_REST, new Dummy(2L, "dummy call"));
		accessLogger.logInterfaceAnswer("EXTERNAL_SERVICE", RequestChannels.HTTP_REST, new Dummy(2L, "dummy callback"), 5L, "200", "OK");

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(2, logs.size());
		
		JsonNode callNode = jsonMapper.readTree(logs.get(0).getFormattedMessage());
		assertEquals("INTERFACE_CALL", callNode.get("transactionStage").asString());
		assertEquals("EXTERNAL_SERVICE", callNode.get("interfaceName").asString());
		assertEquals("HTTP_REST", callNode.get("interfaceChannel").asString());
		
		JsonNode answerNode = jsonMapper.readTree(logs.get(1).getFormattedMessage());
		assertEquals("INTERFACE_ANSWER", answerNode.get("transactionStage").asString());
		assertEquals(5, answerNode.get("responseTimeMillis").asInt());
		assertEquals("200", answerNode.get("responseStatus").asString());
	}

	@Test
	public void testArgsArrayAndNull() throws Exception {
		Object[] args = new Object[]{"test", 1, false};

		accessLogger.logRequest("MY_SERVICE", args);
		accessLogger.logRequest("MY_SERVICE", null);

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(2, logs.size());

		JsonNode arrayNode = jsonMapper.readTree(logs.get(0).getFormattedMessage());
		assertEquals(3, arrayNode.get("requestBody").size());

		JsonNode nullNode = jsonMapper.readTree(logs.get(1).getFormattedMessage());
		assertTrue(nullNode.get("requestBody") == null || nullNode.get("requestBody").isNull());
	}

	@Test
	public void testSensitiveDataMaskedInLogs() throws Exception {
		DummySensitive dummy = new DummySensitive("my_login", "secret_password");

		accessLogger.logRequest("MY_SERVICE", dummy);

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(1, logs.size());

		String logJson = logs.get(0).getFormattedMessage();

		// Verify plain text password NEVER leaked in logs
		assertFalse(logJson.contains("secret_password"));
		assertTrue(logJson.contains("\"password\":\"*****\""));

		JsonNode node = jsonMapper.readTree(logJson);
		assertEquals("my_login", node.get("requestBody").get("login").asString());
		assertEquals("*****", node.get("requestBody").get("password").asString());
	}

	@Test
	public void testSensitiveDataUnmaskedInStandardRestSerialization() throws Exception {
		DummySensitive dummy = new DummySensitive("my_login", "secret_password");

		// Standard serialization outside logging context (e.g. REST response)
		String restJson = jsonMapper.writeValueAsString(dummy);

		// Verify real password IS returned in plain text for non-logging context
		assertTrue(restJson.contains("\"password\":\"secret_password\""));
		assertFalse(restJson.contains("\"password\":\"*****\""));
	}
}
