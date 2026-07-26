package org.ifolks.commons.log.logger;

import org.ifolks.commons.api.filter.SensitiveDataSerializer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import tools.jackson.databind.cfg.ContextAttributes;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

public class JsonMapperFactory {

	public static JsonMapper createDefaultJsonMapper() {
		ContextAttributes defaultAttributes = ContextAttributes.getEmpty()
			.withSharedAttribute(SensitiveDataSerializer.IS_LOGGING_CONTEXT, Boolean.TRUE);

		return JsonMapper.builder()
			.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(Include.NON_NULL))
			.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
			.defaultAttributes(defaultAttributes)
			.build();
	}
}
