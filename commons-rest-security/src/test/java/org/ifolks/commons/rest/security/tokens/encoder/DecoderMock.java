package org.ifolks.commons.rest.security.tokens.encoder;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;

public class DecoderMock implements TokenDecoder<SecurityContextMock> {

	@Override
	public SecurityContextMock decode(String token) {

		String[] tokens = token.split("\\$");
		
		if (tokens.length <2) {
			throw new InvalidTokenException("Bad token");
		}
		
		SecurityContextMock result = new SecurityContextMock();
		result.setApplication(tokens[0]);
		result.setUser(tokens[1]);		
		
		return result;
	}
}
