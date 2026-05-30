package org.ifolks.commons.crypto.encoding;

import org.ifolks.commons.crypto.accessors.AesKeyAccessor;
import org.ifolks.commons.text.serialization.JsonSerializer;

import tools.jackson.databind.json.JsonMapper;

public class AesJsonObjectEncoder extends BasicObjectEncoder {	
		
	public AesJsonObjectEncoder(JsonMapper jsonMapper, AesKeyAccessor keyAccessor) {
		super(new JsonSerializer(jsonMapper), new AesStringEncoder(keyAccessor));
	}
}
