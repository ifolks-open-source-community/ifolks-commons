package org.ifolks.commons.mapper.beans;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.ifolks.commons.mapper.Dummy;
import org.junit.Test;

public class ReflectTest {
	
	@Test
	public void testReflect() {
		
		List<Dummy> list = new ArrayList<Dummy>();
		
		for (TypeVariable<?> variable:list.getClass().getTypeParameters()) {
			System.out.println(variable.getName());
		}
	}
}
