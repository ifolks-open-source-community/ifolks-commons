package org.ifolks.commons.rest.security.context.impl;

import org.ifolks.commons.rest.security.credentials.extractor.impl.FromJwtContextExtractor;
import org.ifolks.commons.rest.security.credentials.validator.SecurityContextValidator;
import org.ifolks.commons.rest.security.tokens.encoder.impl.JwtDecoder;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;
import org.ifolks.commons.rest.security.tokens.verification.TokenVerifier;

/**
 * 
 * @author Nicolas Thibault
 */
public class FromJwtSecurityContextProvider<T extends JsonWebToken<H, B>, H, B> extends FromSignedTokenSecurityContextProvider<JsonWebToken<H, B>, B> {

	public FromJwtSecurityContextProvider(JwtDecoder<T, H, B> jwtDecoder, TokenVerifier<JsonWebToken<H, B>> tokenVerifier, SecurityContextValidator<B> contextValidator) {
		super(jwtDecoder, tokenVerifier, new FromJwtContextExtractor<H, B>(), contextValidator);	
	}
}
