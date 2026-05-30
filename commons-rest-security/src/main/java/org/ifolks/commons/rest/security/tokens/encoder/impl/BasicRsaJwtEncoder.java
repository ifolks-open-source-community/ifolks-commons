package org.ifolks.commons.rest.security.tokens.encoder.impl;

import org.ifolks.commons.crypto.signature.RsaAlgorithms;
import org.ifolks.commons.crypto.signature.RsaSigner;
import org.ifolks.commons.rest.security.tokens.jwt.BasicRsaJsonWebToken;

import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * @author Nicolas Thibault
 */
public class BasicRsaJwtEncoder extends JwtEncoder<BasicRsaJsonWebToken> {
	
	private RsaSigner rsaSigner;
	private RsaAlgorithms algorithm;
	private String keyId;
	
	
	public BasicRsaJwtEncoder(JsonMapper jsonMapper, RsaSigner rsaSigner, String algorithm, String keyId) {
		super(jsonMapper);
		this.rsaSigner = rsaSigner;
		this.algorithm = RsaAlgorithms.valueOf(algorithm);
		this.keyId = keyId;
	}
	

	protected byte[] sign(byte[] payload) {
		return rsaSigner.sign(algorithm, keyId, payload);
	}

}
