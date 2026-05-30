package org.ifolks.commons.rest.security.context;

import org.ifolks.commons.rest.security.credentials.validator.ApplicationContextValidatorMock;
import org.ifolks.commons.rest.security.credentials.validator.SecurityContextValidator;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class FromKeySecurityContextProviderTest {
	
	private static SecurityContextValidator<SecurityContextMock> validator = new ApplicationContextValidatorMock();

	private static SecurityContextProvider provider;
	
	@BeforeAll
	public static void init() {
		provider = new FromMapSecurityContextProviderMock(validator);
	}
	
	@AfterEach
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	@Test
	public void testProvideValidCredentials() {
		provider.provideSecurityContext("IGEN");
		
		SecurityContextMock context = (SecurityContextMock) SecurityContextHolder.getContext();
		Assertions.assertTrue(context.getApplication().equals("IGEN"));
	}
	
	@Test
	public void testProvideBadCredentials() {
		Assertions.assertThrows(InvalidTokenException.class, () -> {
			provider.provideSecurityContext("Fake");
		});
	}
}


