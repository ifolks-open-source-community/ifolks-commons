package org.ifolks.commons.rest.security.context;

import org.ifolks.commons.rest.security.exception.ContextConflictException;
import org.ifolks.commons.rest.security.exception.NoBoundContextException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SecurityContextHolderTest {
	
	@AfterEach
	public void clear() {
		SecurityContextHolder.unbindContext();
	}
	
	
	@Test
	public void testBindNullCredentials() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			SecurityContextHolder.bindContext(null);
		});
	}
	
	
	@Test
	public void testBindCredentials() {
		SecurityContextMock context = new SecurityContextMock();
		SecurityContextHolder.bindContext(context);
		Assertions.assertNotNull(SecurityContextHolder.getContextOrNull());
	}
	
	
	@Test
	public void testBindUserCredentialsConflict() {
		SecurityContextMock context = new SecurityContextMock();
		SecurityContextHolder.bindContext(context);
		Assertions.assertThrows(ContextConflictException.class, () -> {
			SecurityContextHolder.bindContext(context);
		});
	}
	
	
	@Test
	public void testGetNullUserCredentials() {
		Assertions.assertThrows(NoBoundContextException.class, () -> {
			SecurityContextHolder.getContext();
		});
	}
}

