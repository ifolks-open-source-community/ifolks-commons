package org.ifolks.commons.crypto.encoding;

public interface StringEncoder {

	public String encode(String plainText);
	
	public String decode(String cryptedText);
}
