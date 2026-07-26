package org.ifolks.commons.log.data;

public record ErrorLogMessage(
	String errorStatus,
	String errorLabel,
	String errorTrace
) {}
