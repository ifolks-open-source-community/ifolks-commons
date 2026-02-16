package org.ifolks.commons.rest.security.context;

import org.ifolks.commons.rest.security.credentials.validator.ApplicationContextValidatorMock;
import org.ifolks.commons.rest.security.credentials.validator.SecurityContextValidator;
import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class FromKeySecurityContextProviderTest {
	
	private static SecurityContextValidator<SecurityContextMock> validator = new ApplicationContextValidatorMock();

	private static SecurityContextProvider provider;
	
	@BeforeClass
	public static void init() {
		provider = new FromMapSecurityContextProviderMock(validator);
	}
	
	@After
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	@Test
	public void testProvideValidCredentials() {
		provider.provideSecurityContext("IGEN");
		
		SecurityContextMock context = (SecurityContextMock) SecurityContextHolder.getContext();
		Assert.assertTrue(context.getApplication().equals("IGEN"));
	}
	
	@Test(expected=InvalidTokenException.class)
	public void testProvideBadCredentials() {
		provider.provideSecurityContext("Fake");
	}
}
