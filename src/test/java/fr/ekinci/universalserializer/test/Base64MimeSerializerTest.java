package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.base64.Base64Option;
import fr.ekinci.universalserializer.format.text.base64.Base64Serializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class Base64MimeSerializerTest {
	private static final Base64Serializer<ComplexTestClass> s = new Base64Serializer<>(Base64Option.MIME);
	private static final ComplexTestClass origin = instanciateAndInitializeComplexClass();

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException {
		// Serialization
		String ser = s.serialize(origin);

		// Unserialization
		ComplexTestClass generated = s.deserialize(ser);

		// Test
		compareComplexClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws IOException, SerializationException, DeserializationException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			s.sendTo(origin, out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				ComplexTestClass generated = s.receiveFrom(in);

				// Test
				compareComplexClassValues(origin, generated);
			}
		}
	}
}
