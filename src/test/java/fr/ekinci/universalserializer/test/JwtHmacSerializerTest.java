package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.jwt.Algorithm;
import fr.ekinci.universalserializer.format.text.jwt.JwtHmacSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JwtHmacSerializerTest {
	private static final byte[] SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87".getBytes(StandardCharsets.UTF_8);
	private static final JwtHmacSerializer<ComplexTestClass> s = new JwtHmacSerializer<>(ComplexTestClass.class, Algorithm.HS256, SECRET);
	private static final ComplexTestClass origin = instanciateAndInitializeComplexClass();

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException {
		// Serialization (SIGN)
		String ser = s.serialize(origin);

		// Unserialization (VERIFY)
		ComplexTestClass generated = s.deserialize(ser);

		// Test
		compareComplexClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws SerializationException, DeserializationException, IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization (SIGN)
			s.sendTo(origin, out);

			// Unserialization (VERIFY)
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				ComplexTestClass generated = s.receiveFrom(in);

				// Test
				compareComplexClassValues(origin, generated);
			}
		}
	}

}
