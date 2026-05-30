package org.ifolks.commons.rest.security.context;

import org.ifolks.commons.rest.security.context.impl.FromCryptedTokenSecurityContextProvider;
import org.ifolks.commons.rest.security.credentials.validator.SecurityContextValidator;
import org.ifolks.commons.rest.security.credentials.validator.UserContextValidatorMock;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.ifolks.commons.rest.security.tokens.encoder.DecoderMock;
import org.ifolks.commons.rest.security.tokens.encoder.TokenDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class FromCryptedTokenSecurityContextProviderTest {

	private static TokenDecoder<SecurityContextMock> tokenEncoder = new DecoderMock();
	private static SecurityContextValidator<SecurityContextMock> validator = new UserContextValidatorMock();

	private static SecurityContextProvider provider;
	
	@BeforeAll
	public static void init() {
		provider = new FromCryptedTokenSecurityContextProvider<SecurityContextMock>(tokenEncoder, validator);
	}
	
	@AfterEach
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	@Test
	public void testProvideValidCredentials() {
		provider.provideSecurityContext("IGEN$nicolas.thibault@ifolks.org");
		
		SecurityContextMock userCredentials = (SecurityContextMock) SecurityContextHolder.getContext();
		Assertions.assertTrue(userCredentials.getUser().equals("nicolas.thibault@ifolks.org") && userCredentials.getApplication().equals("IGEN"));
	}
	
	@Test
	public void testProvideBadCredentials() {
		Assertions.assertThrows(InvalidTokenException.class, () -> {
			provider.provideSecurityContext("Fake$Thibault");
		});
	}
}


