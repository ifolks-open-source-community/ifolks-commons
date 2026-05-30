package org.ifolks.commons.crypto.encoding;

import java.security.NoSuchAlgorithmException;

import org.ifolks.commons.crypto.accessors.RandomAesKeyAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AesStringEncoderTest {
	
	private static AesStringEncoder encoder;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		encoder = new AesStringEncoder(new RandomAesKeyAccessor());
	}
		
	@Test
	public void testStringEncoder() throws NoSuchAlgorithmException {
		
		String plainText = "test";
		System.out.println(plainText);
		String cryptedText = encoder.encode(plainText);
		System.out.println(cryptedText);
		
		String decryptedText = encoder.decode(cryptedText);
		System.out.println(decryptedText);
		
		Assertions.assertEquals(decryptedText,plainText);
	}
	

}


