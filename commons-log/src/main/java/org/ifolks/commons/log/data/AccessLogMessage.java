package org.ifolks.commons.log.data;

public record AccessLogMessage(
	TransactionStage transactionStage,
	String transactionType,
	Object requestBody,
	Object responseBody,
	Long responseTimeMillis,
	String responseStatus,
	String responseLabel
) {
	public static AccessLogMessage request(String transactionType, Object requestBody) {
		return new AccessLogMessage(TransactionStage.REQUEST, transactionType, requestBody, null, null, null, null);
	}

	public static AccessLogMessage response(String transactionType, Object responseBody, Long responseTimeMillis, String responseStatus, String responseLabel) {
		return new AccessLogMessage(TransactionStage.RESPONSE, transactionType, null, responseBody, responseTimeMillis, responseStatus, responseLabel);
	}
}
