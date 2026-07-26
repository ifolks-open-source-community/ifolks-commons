package org.ifolks.commons.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.ifolks.commons.api.exception.TechnicalError;
import org.ifolks.commons.log.logger.ErrorLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

public class ErrorLoggerTest {

	private ErrorLogger errorLogger;
	private ListAppender<ILoggingEvent> listAppender;
	private JsonMapper jsonMapper = JsonMapper.builder().build();

	@BeforeEach
	public void setUp() {
		errorLogger = new ErrorLogger();
		
		Logger logger = (Logger) LoggerFactory.getLogger("ERROR_LOG");
		listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
	}

	@Test
	public void testLogApplicationException() throws Exception {
		TechnicalError rootCause = new TechnicalError("root cause");
		TechnicalError error = new TechnicalError("error", rootCause);

		errorLogger.logApplicationException(error);

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(1, logs.size());

		String logJson = logs.get(0).getFormattedMessage();
		JsonNode node = jsonMapper.readTree(logJson);

		assertEquals("500", node.get("errorStatus").asString());
		assertEquals("error", node.get("errorLabel").asString());
		assertNotNull(node.get("errorTrace"));
		assertTrue(node.get("errorTrace").asString().contains("root cause"));
	}

	@Test
	public void testLogException() throws Exception {
		IllegalArgumentException ex = new IllegalArgumentException("Invalid argument provided");

		errorLogger.logException(ex);

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(1, logs.size());

		String logJson = logs.get(0).getFormattedMessage();
		JsonNode node = jsonMapper.readTree(logJson);

		assertEquals("500", node.get("errorStatus").asString());
		assertEquals("Invalid argument provided", node.get("errorLabel").asString());
		assertTrue(node.get("errorTrace").asString().contains("IllegalArgumentException"));
	}

	@Test
	public void testLogExceptionWithCustomStatusAndLabel() throws Exception {
		IllegalStateException ex = new IllegalStateException("State error");

		errorLogger.logException(ex, "409", "CONFLICT_STATE");

		List<ILoggingEvent> logs = listAppender.list;
		assertEquals(1, logs.size());

		String logJson = logs.get(0).getFormattedMessage();
		JsonNode node = jsonMapper.readTree(logJson);

		assertEquals("409", node.get("errorStatus").asString());
		assertEquals("CONFLICT_STATE", node.get("errorLabel").asString());
		assertTrue(node.get("errorTrace").asString().contains("IllegalStateException"));
	}
}
