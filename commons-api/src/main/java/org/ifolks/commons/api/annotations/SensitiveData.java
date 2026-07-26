package org.ifolks.commons.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ifolks.commons.api.filter.SensitiveDataSerializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import tools.jackson.databind.annotation.JsonSerialize;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveDataSerializer.class)
public @interface SensitiveData {
}
