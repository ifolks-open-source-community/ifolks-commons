package org.ifolks.commons.rest.security.tokens.verification;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.ifolks.commons.crypto.signature.RsaAlgorithms;
import org.ifolks.commons.crypto.signature.RsaSignatureVerifier;
import org.ifolks.commons.crypto.signature.RsaSigner;
import org.ifolks.commons.rest.security.crypto.RsaPrivateKeyAccessorMock;
import org.ifolks.commons.rest.security.crypto.RsaPublicKeyAccessorMock;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtDecoder;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtEncoder;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.JsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;
import org.ifolks.commons.rest.security.tokens.verification.impl.BasicRsaJwtVerifier;
import org.ifolks.commons.rest.security.tokens.verification.impl.RsaJwtVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.databind.json.JsonMapper;

public class RsaJwtVerifierTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RsaJwtVerifierTest.class);

	private static final BasicRsaJwtEncoder encoder = new BasicRsaJwtEncoder(JsonMapper.builder().build(), new RsaSigner(new RsaPrivateKeyAccessorMock()), RsaAlgorithms.RS256.name(), "test");
	
	private static final BasicRsaJwtDecoder decoder = new BasicRsaJwtDecoder(JsonMapper.builder().build());
	
	private static final RsaJwtVerifier<RsaJwtHeader, BasicJwtBody> verifier = new BasicRsaJwtVerifier(new RsaSignatureVerifier(new RsaPublicKeyAccessorMock()));
	
	@Test
	public void testGoodSignature() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.com");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(null, null);
		jwt.setHeader(header);
		jwt.setBody(body);
		
		String encoded = encoder.encode(jwt);
		
		JsonWebToken<RsaJwtHeader, BasicJwtBody> decoded = decoder.decode(encoded);
		
		verifier.verifyToken(decoded);
		
	}
	
	
	@Test
	public void testBadSignature() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.com");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String encoded = encoder.encode(jwt);
		encoded = encoded + "A";
		
		JsonWebToken<RsaJwtHeader, BasicJwtBody> decoded = decoder.decode(encoded);
		
		Assertions.assertThrows(InvalidTokenException.class, () -> {
			try {
				verifier.verifyToken(decoded);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		});
		
	}
	
	
	@Test
	public void testModifiedBody() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.com");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String encoded = encoder.encode(jwt);
		
		String[] parts = encoded.split("\\.");
		
		BasicJwtBody modifiedBody = new BasicJwtBody();
		modifiedBody.setApplication("IGEN");
		modifiedBody.setUser("nicolas.thibault@ifolks.com");
		String wrongPart = Base64.encodeBase64URLSafeString(JsonMapper.builder().build().writeValueAsBytes(modifiedBody));
		
		String fake = parts[0] + "." + wrongPart + "." + parts[2];
		
		JsonWebToken<RsaJwtHeader, BasicJwtBody> decoded = decoder.decode(fake);
		
		Assertions.assertThrows(InvalidTokenException.class, () -> {
			try {
				verifier.verifyToken(decoded);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		});
		
	}
}

