package org.ifolks.commons.crypto.accessors;

import java.security.PublicKey;

public interface RsaPublicKeyAccessor {

	PublicKey getPublicKey(String keyId);
}
