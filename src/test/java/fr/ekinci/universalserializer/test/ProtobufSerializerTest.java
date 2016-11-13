package fr.ekinci.universalserializer.test;

import static fr.ekinci.universalserializer.test.protocolbuffers.AddressBookProtos.*;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.ProtobufSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.exception.ProtobufSerializerException;
import org.junit.Assert;
import org.junit.Test;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class ProtobufSerializerTest {

	@Test
	public void test1SerializeAndDeserializeWithNativeMethods() {
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
	public void test2SerializeAndDeserialize() {
		try {
			final ProtobufSerializer<Person> serializer = new ProtobufSerializer<>(Person.class);
			final Person origin = Person.newBuilder()
					.setId(123)
					.setName("mock_name")
					.setEmail("mock@email.com")
					.build();

			final Person generated = serializer.deserialize(serializer.serialize(origin));
			Assert.assertEquals(origin.getId(), generated.getId());
			Assert.assertEquals(origin.getName(), generated.getName());
			Assert.assertEquals(origin.getEmail(), generated.getEmail());
		} catch (ProtobufSerializerException | SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}
}
