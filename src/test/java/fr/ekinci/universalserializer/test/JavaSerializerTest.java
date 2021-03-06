package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
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
public class JavaSerializerTest {
	private static final JavaSerializer<ComplexTestClass> s = new JavaSerializer<>();
	private static final ComplexTestClass origin = instanciateAndInitializeComplexClass();

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = s.serialize(origin);

		// Unserialization
		ComplexTestClass generated = s.deserialize(ser);

		// Test
		compareComplexClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws SerializationException, DeserializationException, IOException {
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
