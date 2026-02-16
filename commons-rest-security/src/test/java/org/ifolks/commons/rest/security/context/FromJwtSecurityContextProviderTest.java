package org.ifolks.commons.rest.security.context;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.ifolks.commons.crypto.signature.RsaAlgorithms;
import org.ifolks.commons.crypto.signature.RsaSignatureVerifier;
import org.ifolks.commons.crypto.signature.RsaSigner;
import org.ifolks.commons.rest.security.context.impl.FromBasicRsaJwtSecurityContextProvider;
import org.ifolks.commons.rest.security.credentials.validator.BasicJwtBodyValidatorMock;
import org.ifolks.commons.rest.security.crypto.RsaPrivateKeyAccessorMock;
import org.ifolks.commons.rest.security.crypto.RsaPublicKeyAccessorMock;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtDecoder;
import org.ifolks.commons.rest.security.tokens.encoder.impl.BasicRsaJwtEncoder;
import org.ifolks.commons.rest.security.tokens.jwt.BasicJwtBody;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;
import org.ifolks.commons.rest.security.tokens.jwt.RsaJwtHeader;
import org.ifolks.commons.rest.security.tokens.verification.RsaJwtVerifierTest;
import org.ifolks.commons.rest.security.tokens.verification.impl.BasicRsaJwtVerifier;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FromJwtSecurityContextProviderTest {

	private static final Logger logger = LoggerFactory.getLogger(RsaJwtVerifierTest.class);

	private static final BasicRsaJwtEncoder encoder = new BasicRsaJwtEncoder(new ObjectMapper(), new RsaSigner(new RsaPrivateKeyAccessorMock()), RsaAlgorithms.RS256.name(), "test");

	private static final FromBasicRsaJwtSecurityContextProvider provider;
	
	static {
		provider = new FromBasicRsaJwtSecurityContextProvider(new BasicRsaJwtDecoder(new ObjectMapper()), new BasicRsaJwtVerifier(new RsaSignatureVerifier(new RsaPublicKeyAccessorMock())), new BasicJwtBodyValidatorMock());
	}
	
	@After
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	@Test
	public void testGoodToken() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.org");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String token = encoder.encode(jwt);
		
		provider.provideSecurityContext(token);
		
		BasicJwtBody context = (BasicJwtBody) SecurityContextHolder.getContext();
		Assert.assertTrue(context.getUser().equals("nicolas.thibault@ifolks.org") && context.getApplication().equals("IGEN"));
		
	}
	
	
	@Test(expected=InvalidTokenException.class)
	public void testInvalidCredentials() {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.com");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String token = encoder.encode(jwt);
		
		try {
			provider.provideSecurityContext(token);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
	}
	
	
	@Test(expected=InvalidTokenException.class)
	public void testModifiedBody() throws JsonProcessingException {
		
		BasicJwtBody body = new BasicJwtBody();
		body.setApplication("IGEN");
		body.setUser("nicolas.thibault@ifolks.org");
		body.setExpiryDate(Date.from(Instant.now().plusSeconds(3600)));
		
		RsaJwtHeader header = new RsaJwtHeader(RsaAlgorithms.RS256, "test");
		
		BasicRsaJsonWebToken jwt = new BasicRsaJsonWebToken(header, body);
		
		String token = encoder.encode(jwt);
		
		String[] parts = token.split("\\.");
		
		BasicJwtBody modifiedBody = new BasicJwtBody();
		modifiedBody.setApplication("IGEN");
		modifiedBody.setUser("nicolas.thibault@ifolks.com");
		String wrongPart = Base64.encodeBase64URLSafeString(new ObjectMapper().writeValueAsBytes(modifiedBody));
		
		String fake = parts[0] + "." + wrongPart + "." + parts[2];
		
		try {
			provider.provideSecurityContext(fake);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
	}
}
