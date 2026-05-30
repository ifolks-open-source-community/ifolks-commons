package org.ifolks.commons.text.serialization;

import org.ifolks.commons.text.serialization.exceptions.SerializationException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * 
 * Implementation of a {@link Serializer} in Json that uses a jackson {@link JsonMapper}
 * 
 * @author Nicolas Thibault
 *
 */
public class JsonSerializer implements Serializer {
	
	private JsonMapper jsonMapper;

	public JsonSerializer(JsonMapper jsonMapper) {
		super();
		this.jsonMapper = jsonMapper;
	}

	@Override
	public String serialize(Object object) {
		
		try {
			return jsonMapper.writeValueAsString(object) ;
		} catch (JacksonException e) {
			throw new SerializationException("failed to serialize object : " + e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(String arg, Class<T> targetClass) {
		
		try {
			return jsonMapper.readValue(arg, targetClass);
		} catch (JacksonException e) {
			throw new SerializationException("failed to deserialize object : " + e.getMessage(), e);
		}
	}

}
