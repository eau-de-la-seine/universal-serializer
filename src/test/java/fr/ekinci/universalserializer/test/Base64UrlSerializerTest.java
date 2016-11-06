package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.base64.AbstractBase64Serializer;
import fr.ekinci.universalserializer.format.text.base64.Base64UrlSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class Base64UrlSerializerTest {

	@Test
	public void testSerializeAndUnserialize() {
		AbstractBase64Serializer s = new Base64UrlSerializer();
		ComplexTestClass origin = instanciateAndInitializeComplexClass();

		try {
			// Serialization
			String ser = s.serialize(origin);

			// Unserialization
			ComplexTestClass generated = s.unserialize(ser);

			// Test
			compareComplexClassValues(origin, generated);
		} catch (SerializationException | UnserializationException e) {
			e.printStackTrace();
		}
	}
}
