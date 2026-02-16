package org.ifolks.commons.rest.security.tokens.encoder.impl;

import org.ifolks.commons.crypto.accessors.AesKeyAccessor;
import org.ifolks.commons.crypto.encoding.AesJsonObjectEncoder;
import org.ifolks.commons.rest.security.tokens.encoder.TokenEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * imlementation of a {@link TokenEncoder} that uses an {@link AesJsonObjectEncoder}
 *
 * @author Nicolas Thibault, Abdessalam El Jai, Alexandre Rupp
 */
public class AesJsonTokenDecoder<T> extends CryptedTokenDecoder<T> {	
	
	public AesJsonTokenDecoder(ObjectMapper objectMapper, AesKeyAccessor keyAccessor, Class<T> tokenClass) {	
		super(new AesJsonObjectEncoder(objectMapper, keyAccessor), tokenClass);
	}
}
