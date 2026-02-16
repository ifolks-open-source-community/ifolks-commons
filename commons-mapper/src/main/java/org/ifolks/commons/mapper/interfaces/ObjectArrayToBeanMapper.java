package org.ifolks.commons.mapper.interfaces;

public interface ObjectArrayToBeanMapper<T> {
	
	T mapFrom(T obj, Object[] objectArray, int startField);
	
	T mapFrom(T obj, Object[] objectArray);
	
}
