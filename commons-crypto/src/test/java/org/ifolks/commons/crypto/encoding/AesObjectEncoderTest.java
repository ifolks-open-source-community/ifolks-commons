package org.ifolks.commons.crypto.encoding;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.ifolks.commons.crypto.accessors.RandomAesKeyAccessor;
import org.ifolks.commons.crypto.miscellaneous.TestObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.json.JsonMapper;

public class AesObjectEncoderTest {
	
	
	private static AesJsonObjectEncoder objectEncoder;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		objectEncoder = new AesJsonObjectEncoder(JsonMapper.builder().build(), new RandomAesKeyAccessor());
	}

	@Test
	public void testObjectEncoder() throws NoSuchAlgorithmException {
		
		TestObject plainObject = new TestObject("test", new Date());
		System.out.println(plainObject);
		
		String cryptedText = objectEncoder.encode(plainObject);
		System.out.println(cryptedText);
		
		TestObject decryptedObject = objectEncoder.decode(cryptedText, TestObject.class);
		System.out.println(decryptedObject);
		
		Assertions.assertEquals(decryptedObject,plainObject);
		
	}
		
}



