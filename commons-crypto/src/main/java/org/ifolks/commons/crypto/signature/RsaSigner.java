package org.ifolks.commons.crypto.signature;

import java.security.Signature;

import org.ifolks.commons.crypto.accessors.RsaPrivateKeyAccessor;
import org.ifolks.commons.crypto.exception.SignatureException;

public class RsaSigner {
	
	public RsaSigner(RsaPrivateKeyAccessor rsaPrivateKeyAccessor) {
		this.rsaPrivateKeyAccessor = rsaPrivateKeyAccessor;
	}
	
	private RsaPrivateKeyAccessor rsaPrivateKeyAccessor;
	

	public byte[] sign(RsaAlgorithms algorithm, String keyId, byte[] data) {
		try {
			Signature signature = Signature.getInstance(algorithm.getFullName());
	        signature.initSign(rsaPrivateKeyAccessor.getPrivateKey(keyId));
	        signature.update(data);
	        return signature.sign();
		} catch (Exception e) {
			throw new SignatureException("failed to sign", e);
		}
	}
}
