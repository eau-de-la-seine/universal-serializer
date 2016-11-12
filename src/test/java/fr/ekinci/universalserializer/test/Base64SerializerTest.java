package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.base64.AbstractBase64Serializer;
import fr.ekinci.universalserializer.format.text.base64.Base64Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class Base64SerializerTest {

	@Test
	public void testSerializeAndUnserialize() {
		AbstractBase64Serializer<ComplexTestClass> s = new Base64Serializer<>();
		ComplexTestClass origin = instanciateAndInitializeComplexClass();

		try {
			// Serialization
			String ser = s.serialize(origin);

			// Unserialization
			ComplexTestClass generated = s.deserialize(ser);

			// Test
			compareComplexClassValues(origin, generated);
		} catch (SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}
}
