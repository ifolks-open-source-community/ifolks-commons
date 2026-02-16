package org.ifolks.commons.crypto.encoding;

import org.ifolks.commons.crypto.accessors.AesKeyAccessor;
import org.ifolks.commons.text.serialization.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AesJsonObjectEncoder extends BasicObjectEncoder {	
		
	public AesJsonObjectEncoder(ObjectMapper objectMapper, AesKeyAccessor keyAccessor) {
		super(new JsonSerializer(objectMapper), new AesStringEncoder(keyAccessor));
	}
}
