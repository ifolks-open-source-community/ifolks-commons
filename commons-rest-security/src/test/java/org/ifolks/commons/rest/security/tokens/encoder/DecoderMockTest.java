package org.ifolks.commons.rest.security.tokens.encoder;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecoderMockTest {
	
	private static DecoderMock decoder;
	
	@BeforeClass
	public static void init() {
		decoder = new DecoderMock();
	}
	
	@Test
	public void testGoodContext() {
		SecurityContextMock context = decoder.decode("IGEN$nicolas.thibault@ifolks.org");
		System.out.println(context);
	}
	
	@Test(expected = InvalidTokenException.class)
	public void testBadContext() {
		decoder.decode("Test");
	}

}
