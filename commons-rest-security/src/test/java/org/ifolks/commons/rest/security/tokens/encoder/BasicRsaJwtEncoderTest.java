package org.ifolks.commons.rest.security.tokens.encoder;

import org.ifolks.commons.crypto.signature.RsaAlgorithms;
import org.ifolks.commons.crypto.signature.RsaSigner;
import org.ifolks.commons.rest.security.crypto.RsaPrivateKeyAccessorMock;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtDecoder;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtEncoder;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.databind.json.JsonMapper;

public class BasicRsaJwtEncoderTest {
	
	private static final Logger logger = LoggerFactory.getLogger(BasicRsaJwtEncoderTest.class);

	private static final BasicRsaJwtEncoder encoder = new BasicRsaJwtEncoder(JsonMapper.builder().build(), new RsaSigner(new RsaPrivateKeyAccessorMock()), RsaAlgorithms.RS256.name(), "test");
	
	private static final BasicRsaJwtDecoder decoder = new BasicRsaJwtDecoder(JsonMapper.builder().build());
	
	@Test
	public void test() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.org");
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String encoded = encoder.encode(jwt);
		logger.debug(encoded);
		
		JsonWebToken<RsaJwtHeader, BasicJwtBody> decoded = decoder.decode(encoded);
		logger.debug(decoded.getHeader().getAlgorithm());
		logger.debug(decoded.getHeader().getPublicKeyId());
		logger.debug(decoded.getBody().getApplication());
		logger.debug(decoded.getBody().getUser());
		
		Assertions.assertTrue(decoded.getHeader().getAlgorithm().equals(RsaAlgorithms.RS256.name()));
		Assertions.assertTrue(decoded.getHeader().getPublicKeyId().equals("test"));
		Assertions.assertTrue(decoded.getBody().getApplication().equals("IGEN"));
		Assertions.assertTrue(decoded.getBody().getUser().equals("nicolas.thibault@ifolks.org"));
	}
}

