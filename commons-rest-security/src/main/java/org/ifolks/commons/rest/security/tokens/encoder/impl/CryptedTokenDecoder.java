package org.ifolks.commons.rest.security.tokens.encoder.impl;

import org.ifolks.commons.crypto.encoding.ObjectEncoder;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.encoder.TokenDecoder;
import org.ifolks.commons.rest.security.tokens.encoder.TokenEncoder;

/**
 * imlementation of a {@link TokenEncoder} that uses an {@link ObjectEncoder}
 *
 * @author Nicolas Thibault, Abdessalam El Jai, Alexandre Rupp
 */
public class CryptedTokenDecoder<T> implements TokenDecoder<T> {
	
	private ObjectEncoder objectEncoder;
	private Class<T> tokenClass;
	
	
	public CryptedTokenDecoder(ObjectEncoder objectEncoder, Class<T> tokenClass) {	
		this.objectEncoder = objectEncoder;
		this.tokenClass = tokenClass;
	}	
	
	@Override
	public T decode (String token) {
		
		try {
			return objectEncoder.decode(token, tokenClass);
		} catch (Exception e) {
			throw new InvalidTokenException("token.invalid", e);
		}
	}
}
