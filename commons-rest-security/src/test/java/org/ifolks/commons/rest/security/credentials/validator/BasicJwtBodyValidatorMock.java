package org.ifolks.commons.rest.security.credentials.validator;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;

public class BasicJwtBodyValidatorMock implements SecurityContextValidator<BasicJwtBody> {

	@Override
	public void validateContext(BasicJwtBody securityCredentials) {
		if (!securityCredentials.getUser().equals("nicolas.thibault@ifolks.org")) {
			throw new InvalidTokenException("Bad credentials");
		}
	}
}
