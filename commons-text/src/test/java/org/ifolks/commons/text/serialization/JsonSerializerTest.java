package org.ifolks.commons.text.serialization;

import org.ifolks.commons.text.miscellanous.Dummy;
import org.ifolks.commons.text.miscellanous.Fool;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.jackson.databind.json.JsonMapper;

public class JsonSerializerTest {

private static Serializer serializer;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		serializer = new JsonSerializer(JsonMapper.builder().build());
	}

	@Test
	public void testFool() {
		
		Fool fool = new Fool();
		fool.setName("testFool");
		
		String serializedFool = serializer.serialize(fool);
		System.out.println(serializedFool);
		Fool deserialized = serializer.deserialize(serializedFool, Fool.class);
		
		Assert.assertTrue(deserialized.equals(fool));
		
	}
	
	@Test
	public void testDummy() {
		
		Fool fool = new Fool();
		fool.setName("testFool");
		Dummy dummy = new Dummy();
		dummy.setName("testDummy");
		dummy.setFool(fool);
		
		String serializedDummy = serializer.serialize(dummy);
		System.out.println(serializedDummy);
		Dummy deserialized = serializer.deserialize(serializedDummy, Dummy.class);
		
		Assert.assertTrue(deserialized.equals(dummy));
		
	}
}
