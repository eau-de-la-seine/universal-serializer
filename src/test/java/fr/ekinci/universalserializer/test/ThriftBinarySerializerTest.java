package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.thrift.ThriftOption;
import fr.ekinci.universalserializer.format.binary.thrift.ThriftSerializer;
import fr.ekinci.universalserializer.format.binary.thrift.exception.ThriftSerializerException;
import fr.ekinci.universalserializer.test.thrift.ThriftComplexTestClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeThriftComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class ThriftBinarySerializerTest {
	private static ThriftSerializer<ThriftComplexTestClass> s;
	private static final ThriftComplexTestClass origin = instanciateAndInitializeThriftComplexClass();

	@BeforeClass
	public static void staticInit() throws ThriftSerializerException {
		s = new ThriftSerializer<>(ThriftComplexTestClass.class, ThriftOption.BINARY);
	}

	@Test
	public void testSerializeAndDeserialize() throws ThriftSerializerException, SerializationException, DeserializationException {
		// Serialization
		byte[] ser = s.serialize(origin);

		// Unserialization
		ThriftComplexTestClass generated = s.deserialize(ser);

		// Test
		compareComplexClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws SerializationException, DeserializationException, IOException, ThriftSerializerException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			s.sendTo(origin, out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				ThriftComplexTestClass generated = s.receiveFrom(in);

				// Test
				compareComplexClassValues(origin, generated);
			}
		}
	}
}
