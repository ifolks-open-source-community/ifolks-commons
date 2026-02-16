package org.ifolks.commons.rest.security.tokens.encoder;

/**
 * used to encode a Token to a String
 * 
 * @author Nicolas Thibault, Abdessalam El Jai, Alexandre Rupp
 *
 */
public interface TokenEncoder<T> {

	String encode(T token);
}
