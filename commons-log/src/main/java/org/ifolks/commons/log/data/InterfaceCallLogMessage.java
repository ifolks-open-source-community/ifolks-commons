package org.ifolks.commons.log.data;

import org.ifolks.commons.log.context.RequestChannels;

public record InterfaceCallLogMessage(
	TransactionStage transactionStage,
	String interfaceName,
	RequestChannels interfaceChannel,
	Object sentBody,
	Object receivedBody,
	Long responseTimeMillis,
	String responseStatus,
	String responseLabel
) {
	public static InterfaceCallLogMessage call(String interfaceName, RequestChannels interfaceChannel, Object sentBody) {
		return new InterfaceCallLogMessage(TransactionStage.INTERFACE_CALL, interfaceName, interfaceChannel, sentBody, null, null, null, null);
	}

	public static InterfaceCallLogMessage answer(String interfaceName, RequestChannels interfaceChannel, Object receivedBody, Long responseTimeMillis, String responseStatus, String responseLabel) {
		return new InterfaceCallLogMessage(TransactionStage.INTERFACE_ANSWER, interfaceName, interfaceChannel, null, receivedBody, responseTimeMillis, responseStatus, responseLabel);
	}
}
