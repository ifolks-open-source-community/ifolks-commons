package org.ifolks.commons.mapper.impl;

import org.ifolks.commons.mapper.beans.AccessibleField;
import org.ifolks.commons.mapper.beans.MappableBean;
import org.ifolks.commons.mapper.beans.MappableBeanFactory;
import org.ifolks.commons.mapper.interfaces.ObjectArrayToBeanMapper;

/**
 * Enables the copy of a bean from an object array
 * @author Nicolas Thibault
 *
 * @param <T>
 */
public class ObjectArrayToBeanMapperImpl<T> implements ObjectArrayToBeanMapper<T> {
	
	private final MappableBean<T> mappableBean;
	
	public ObjectArrayToBeanMapperImpl (Class<T> clazz) {
		mappableBean = MappableBeanFactory.getMappableBean(clazz);
	}

	@Override
	public T mapFrom(T obj, Object[] objectArray, int startField) {
		
		for (int i = 0;i<objectArray.length;i++) {
			
			AccessibleField accessibleField = mappableBean.accessibleFields.get(i+startField);
			Object dbValue = objectArray[i];
			
			Object value = DbObjectToObjectConverter.getObjectFromDbObject(dbValue, accessibleField.fieldClass);
			
			accessibleField.setValue(value, obj);
		}
		
		return obj;
	}
	
	@Override
	public T mapFrom(T obj, Object[] objectArray) {
		
		return mapFrom(obj, objectArray, 0);
	}
}
