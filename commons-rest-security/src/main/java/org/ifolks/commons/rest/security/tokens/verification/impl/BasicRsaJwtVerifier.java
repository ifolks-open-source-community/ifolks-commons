package org.ifolks.commons.rest.security.tokens.verification.impl;

import java.util.Date;

import org.ifolks.commons.crypto.signature.RsaSignatureVerifier;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;

/**
 *
 * @param <T>
 * 
 * @author Nicolas Thibault
 * 
 */
public class BasicRsaJwtVerifier extends RsaJwtVerifier<RsaJwtHeader, BasicJwtBody> {

	public BasicRsaJwtVerifier(RsaSignatureVerifier rsaSignatureverifier) {
		super(rsaSignatureverifier);
	}

	@Override
	protected void verifyBody(BasicJwtBody body) {
		if (new Date().after(body.getExpiryDate())) {
			throw new InvalidTokenException("token.expired");
		}
	}	
}
