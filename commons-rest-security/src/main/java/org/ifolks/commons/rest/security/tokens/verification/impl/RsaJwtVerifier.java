package org.ifolks.commons.rest.security.tokens.verification.impl;

import org.ifolks.commons.crypto.signature.RsaAlgorithms;
import org.ifolks.commons.crypto.signature.RsaSignatureVerifier;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;
import org.ifolks.commons.rest.security.tokens.verification.TokenVerifier;

/**
 *
 * 
 * @author Nicolas Thibault
 * 
 */
public abstract class RsaJwtVerifier<H extends RsaJwtHeader, B> implements TokenVerifier<JsonWebToken<H, B>> {

	private RsaSignatureVerifier rsaSignatureverifier;
	
	
	public RsaJwtVerifier(RsaSignatureVerifier rsaSignatureverifier) {
		super();
		this.rsaSignatureverifier = rsaSignatureverifier;
	}



	@Override
	public void verifyToken(JsonWebToken<H, B> token) {
		RsaAlgorithms rsAalgorithm = RsaAlgorithms.valueOf(token.getHeader().getAlgorithm());
		if (rsAalgorithm == null) {
			throw new InvalidTokenException("algorithm.unsupported");
		}
		boolean valid = rsaSignatureverifier.checkSignature(rsAalgorithm, token.getHeader().getPublicKeyId(), token.getPayload(), token.getSignature());
		
		if (!valid) {
			throw new InvalidTokenException("signature.wrong");
		}
		
		verifyBody(token.getBody());
	}

	protected abstract void verifyBody(B body);
}
