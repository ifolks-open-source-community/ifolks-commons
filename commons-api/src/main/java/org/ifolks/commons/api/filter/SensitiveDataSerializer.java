package org.ifolks.commons.api.filter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class SensitiveDataSerializer extends ValueSerializer<Object> {

	public static final String IS_LOGGING_CONTEXT = "IS_LOGGING_CONTEXT";

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializationContext ctxt) {
		Object isLogging = ctxt.getAttribute(IS_LOGGING_CONTEXT);
		if (Boolean.TRUE.equals(isLogging)) {
			if (value == null) {
				gen.writeNull();
			} else {
				gen.writeString("*****");
			}
		} else {
			if (value == null) {
				gen.writeNull();
			} else {
				gen.writeString(String.valueOf(value));
			}
		}
	}
}
