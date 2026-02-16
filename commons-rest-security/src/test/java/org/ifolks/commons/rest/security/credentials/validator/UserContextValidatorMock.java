package org.ifolks.commons.rest.security.credentials.validator;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;

public class UserContextValidatorMock implements SecurityContextValidator<SecurityContextMock> {

	@Override
	public void validateContext(SecurityContextMock securityCredentials) {
		if (!(securityCredentials.getUser().equals("nicolas.thibault@ifolks.org") && securityCredentials.getApplication().equals("IGEN"))) {
			throw new InvalidTokenException("Bad credentials");
		}
	}
}
