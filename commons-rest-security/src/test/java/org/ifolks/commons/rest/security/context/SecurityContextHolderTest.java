package org.ifolks.commons.rest.security.context;

import org.ifolks.commons.rest.security.exception.ContextConflictException;
import org.ifolks.commons.rest.security.exception.NoBoundContextException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SecurityContextHolderTest {
	
	@After
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	
	@Test(expected=NullPointerException.class)
	public void testBindNullCredentials() {
		SecurityContextHolder.bindContext(null);
	}
	
	
	@Test
	public void testBindCredentials() {
		SecurityContextMock context = new SecurityContextMock();
		SecurityContextHolder.bindContext(context);
		Assert.assertNotNull(SecurityContextHolder.getContextOrNull());
	}
	
	
	@Test(expected=ContextConflictException.class)
	public void testBindUserCredentialsConflict() {
		SecurityContextMock context = new SecurityContextMock();
		SecurityContextHolder.bindContext(context);
		SecurityContextHolder.bindContext(context);
	}
	
	
	@Test(expected=NoBoundContextException.class)
	public void testGetNullUserCredentials() {
		SecurityContextHolder.getContext();
	}
}
