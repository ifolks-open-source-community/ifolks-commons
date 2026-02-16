package org.ifolks.commons.rest.security.credentials.extractor.impl;

import org.ifolks.commons.rest.security.credentials.extractor.SecurityContextExtractor;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;

public class FromJwtContextExtractor<H, B> implements SecurityContextExtractor<JsonWebToken<H, B>, B> {

	@Override
	public B extractContext(JsonWebToken<H, B> token) {
		return token.getBody();
	}
}
