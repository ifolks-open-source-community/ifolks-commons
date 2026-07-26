 package org.ifolks.commons.log;

import org.ifolks.commons.api.annotations.SensitiveData;

public record DummySensitive (
	
	String login,
	@SensitiveData
	String password
	
	) {
	
}
