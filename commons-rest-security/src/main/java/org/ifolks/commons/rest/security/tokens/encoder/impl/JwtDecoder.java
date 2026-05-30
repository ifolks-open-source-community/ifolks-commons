package org.ifolks.commons.rest.security.tokens.encoder.impl;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.encoder.TokenDecoder;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;

import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * @author Nicolas Thibault
 *
 * @param <T>
 * @param <H>
 * @param <B>
 */
public class JwtDecoder<T extends JsonWebToken<H, B>, H, B> implements TokenDecoder<JsonWebToken<H, B>> {
	
	private JsonMapper jsonMapper;
	private Class<T> tokenClass;
	private Class<H> headerClass;
	private Class<B> bodyClass;

	public JwtDecoder(JsonMapper jsonMapper, Class<T> tokenClass, Class<H> headerClass, Class<B> bodyClass) {
		super();
		this.jsonMapper = jsonMapper;
		this.tokenClass = tokenClass;
		this.headerClass = headerClass;
		this.bodyClass = bodyClass;
	}
	
	


	@Override
	public T decode(String token) {
		String[] parts = token.split("\\.");
		try {
			T result = tokenClass.getDeclaredConstructor().newInstance();		
			result.setHeader(jsonMapper.readValue(Base64.decodeBase64(parts[0]), headerClass));
			result.setBody(jsonMapper.readValue(Base64.decodeBase64(parts[1]), bodyClass));
			result.setSignature(Base64.decodeBase64(parts[2]));
			result.setPayload((parts[0] + "." + parts[1]).getBytes(StandardCharsets.UTF_8));
			
			return result;
		} catch (Exception e) {
			throw new InvalidTokenException(e.getMessage(), e);
		}
	}
}
