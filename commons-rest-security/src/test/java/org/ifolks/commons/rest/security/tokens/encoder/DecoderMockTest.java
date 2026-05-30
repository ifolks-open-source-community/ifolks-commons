package org.ifolks.commons.rest.security.tokens.encoder;

import org.ifolks.commons.rest.security.exception.InvalidTokenException;
import org.ifolks.commons.rest.security.tokens.SecurityContextMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DecoderMockTest {
	
	private static DecoderMock decoder;
	
	@BeforeAll
	public static void init() {
		decoder = new DecoderMock();
	}
	
	@Test
	public void testGoodContext() {
		SecurityContextMock context = decoder.decode("IGEN$nicolas.thibault@ifolks.org");
		System.out.println(context);
	}
	
	@Test
	public void testBadContext() {
		Assertions.assertThrows(InvalidTokenException.class, () -> {
			decoder.decode("Test");
		});
	}

}


