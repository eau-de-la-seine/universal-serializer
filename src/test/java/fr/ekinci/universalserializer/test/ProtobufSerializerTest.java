package fr.ekinci.universalserializer.test;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.ProtobufSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.exception.ProtobufSerializerException;
import fr.ekinci.universalserializer.test.protocolbuffers.ProtoComplexTestClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static fr.ekinci.universalserializer.test.protocolbuffers.AddressBookProtos.Person;
import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeProtoComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class ProtobufSerializerTest {
	private static ProtobufSerializer<ProtoComplexTestClass.ComplexTestClass> s;
	private static final ProtoComplexTestClass.ComplexTestClass origin = instanciateAndInitializeProtoComplexClass();

	@BeforeClass
	public static void staticInit() throws ProtobufSerializerException {
		s = new ProtobufSerializer<>(ProtoComplexTestClass.ComplexTestClass.class);
	}

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = s.serialize(origin);

		// Unserialization
		ProtoComplexTestClass.ComplexTestClass generated = s.deserialize(ser);

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
				ProtoComplexTestClass.ComplexTestClass generated = s.receiveFrom(in);

				// Test
				compareComplexClassValues(origin, generated);
			}
		}
	}

	@Test
	public void testSerializeAndDeserialize_Person_withNativeMethods() {
		final Person origin = Person.newBuilder()
				.setId(123)
				.setName("mock_name")
				.setEmail("mock@email.com")
				.build();
		try {
			final Person generated = Person.parseFrom(origin.toByteArray());
			Assert.assertEquals(origin.getId(), generated.getId());
			Assert.assertEquals(origin.getName(), generated.getName());
			Assert.assertEquals(origin.getEmail(), generated.getEmail());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSerializeAndDeserialize_Person_withoutNativeMethods() throws ProtobufSerializerException, SerializationException, DeserializationException {
		final ProtobufSerializer<Person> s = new ProtobufSerializer<>(Person.class);
		final Person origin = Person.newBuilder()
			.setId(123)
			.setName("mock_name")
			.setEmail("mock@email.com")
			.build();

		// Serialization
		byte[] ser = s.serialize(origin);

		// Unserialization
		final Person generated = s.deserialize(ser);

		// Test
		Assert.assertEquals(origin.getId(), generated.getId());
		Assert.assertEquals(origin.getName(), generated.getName());
		Assert.assertEquals(origin.getEmail(), generated.getEmail());
	}

	@Test
	public void testSerializeAndDeserialize_Person_stream_withNativeMethods() throws IOException {
		final Person origin = Person.newBuilder()
				.setId(123)
				.setName("mock_name")
				.setEmail("mock@email.com")
				.build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			origin.writeTo(out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				Person generated = Person.parseFrom(in);

				// Test
				Assert.assertEquals(origin.getId(), generated.getId());
				Assert.assertEquals(origin.getName(), generated.getName());
				Assert.assertEquals(origin.getEmail(), generated.getEmail());
			}
		}
	}

	@Test
	public void testSerializeAndDeserialize_Person_stream_withoutNativeMethods() throws ProtobufSerializerException, SerializationException, DeserializationException, IOException {
		final ProtobufSerializer<Person> s = new ProtobufSerializer<>(Person.class);
		final Person origin = Person.newBuilder()
				.setId(123)
				.setName("mock_name")
				.setEmail("mock@email.com")
				.build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			s.sendTo(origin, out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				Person generated = s.receiveFrom(in);

				// Test
				Assert.assertEquals(origin.getId(), generated.getId());
				Assert.assertEquals(origin.getName(), generated.getName());
				Assert.assertEquals(origin.getEmail(), generated.getEmail());
			}
		}
	}
}
