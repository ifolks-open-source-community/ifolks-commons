package org.ifolks.commons.rest.security.context.impl;

import org.ifolks.commons.rest.security.credentials.validator.SecurityContextValidator;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtDecoder;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;
import org.ifolks.commons.rest.security.tokens.verification.impl.BasicRsaJwtVerifier;

/**
 * 
 * @author Nicolas Thibault
 */
public class FromBasicRsaJwtSecurityContextProvider extends FromJwtSecurityContextProvider<BasicRsaJsonWebToken, RsaJwtHeader, BasicJwtBody> {

	public FromBasicRsaJwtSecurityContextProvider(BasicRsaJwtDecoder decoder, BasicRsaJwtVerifier verifier, SecurityContextValidator<BasicJwtBody> contextValidator) {
		super(decoder, verifier, contextValidator);
		
	}
}
