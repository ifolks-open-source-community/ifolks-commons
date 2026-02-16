package org.ifolks.commons.rest.security.credentials.validator;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;

public class ApplicationContextValidatorMock implements SecurityContextValidator<SecurityContextMock> {

	@Override
	public void validateContext(SecurityContextMock context) {
		if (!(context.getApplication().equals("IGEN"))) {
			throw new InvalidTokenException("Bad credentials");
		}
	}
}
