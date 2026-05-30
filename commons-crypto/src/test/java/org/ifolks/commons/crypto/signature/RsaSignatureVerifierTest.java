package org.ifolks.commons.crypto.signature;

import java.nio.charset.StandardCharsets;

import org.ifolks.commons.crypto.accessors.RsaPrivateKeyAccessorMock;
import org.ifolks.commons.crypto.accessors.RsaPublicKeyAccessorMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RsaSignatureVerifierTest {

	private static RsaSigner signer;
	private static RsaSignatureVerifier verifier;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		signer = new RsaSigner(new RsaPrivateKeyAccessorMock());
		verifier = new RsaSignatureVerifier(new RsaPublicKeyAccessorMock());
	}
		
	@Test
	public void testCheck() {
		
		byte[] data = "test".getBytes(StandardCharsets.UTF_8);
		byte[] signature = signer.sign(RsaAlgorithms.RS256, "test", data);
		boolean checked = verifier.checkSignature(RsaAlgorithms.RS256, "test", data, signature);
		
		Assertions.assertTrue(checked);
	}
}


