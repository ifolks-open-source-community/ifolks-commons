package org.ifolks.commons.rest.security.tokens.encoder.impl;

import org.ifolks.commons.crypto.encoding.ObjectEncoder;
import org.ifolks.commons.rest.security.tokens.encoder.TokenEncoder;

/**
 * imlementation of a {@link TokenEncoder} that uses an {@link ObjectEncoder}
 *
 * @author Nicolas Thibault, Abdessalam El Jai, Alexandre Rupp
 */
public class CryptedTokenEncoder<T> implements TokenEncoder<T> {
	
	private ObjectEncoder objectEncoder;
	
	
	public CryptedTokenEncoder(ObjectEncoder objectEncoder) {	
		this.objectEncoder = objectEncoder;
	}	
	
	@Override
	public String encode (T token) {
		
		return objectEncoder.encode(token);
	}
}
