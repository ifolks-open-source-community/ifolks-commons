package org.ifolks.commons.rest.security.tokens.encoder.impl;

import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;

import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * @author Nicolas Thibault
 *
 */
public class BasicRsaJwtDecoder extends JwtDecoder<BasicRsaJsonWebToken, RsaJwtHeader, BasicJwtBody> {
	
	
	public BasicRsaJwtDecoder(JsonMapper jsonMapper) {
		super(jsonMapper, BasicRsaJsonWebToken.class, RsaJwtHeader.class, BasicJwtBody.class);
	}
}
