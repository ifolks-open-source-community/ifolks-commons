package org.ifolks.commons.rest.security.tokens.encoder.impl;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.ifolks.commons.rest.security.exception.TokenEncodingException;
import org.ifolks.commons.rest.security.tokens.encoder.TokenEncoder;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * @author Nicolas Thibault
 *
 * @param <T>
 * @param <H>
 * @param <B>
 */
public abstract class JwtEncoder<T extends JsonWebToken> implements TokenEncoder<T> {
	
	private JsonMapper jsonMapper;
	

	public JwtEncoder(JsonMapper jsonMapper) {
		super();
		this.jsonMapper = jsonMapper;
	}
	
	

	@Override
	public String encode(T token) {
		String result = "";
		String headerPart = "";
		String bodyPart = "";
		byte[] payload = null;
		String signaturePart = "";
		
		try {
			headerPart += Base64.encodeBase64URLSafeString(jsonMapper.writeValueAsBytes(token.getHeader()));
			bodyPart += Base64.encodeBase64URLSafeString(jsonMapper.writeValueAsBytes(token.getBody()));
			result = headerPart + "." + bodyPart;
			payload = result.getBytes(StandardCharsets.UTF_8);
			signaturePart = Base64.encodeBase64URLSafeString(sign(payload));
			result = result + "." + signaturePart;
			
		} catch (JacksonException e) {
			throw new TokenEncodingException("Failed to encode token", e);
		}
		
		return result;
	}



	protected abstract byte[] sign(byte[] payload);

}
