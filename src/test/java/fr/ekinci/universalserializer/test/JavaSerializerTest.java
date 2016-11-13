package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JavaSerializerTest {

	@Test
	public void testSerializeAndDeserialize() {
		JavaSerializer<ComplexTestClass> s = new JavaSerializer<>();
		ComplexTestClass origin = instanciateAndInitializeComplexClass();

		try {
			// Serialization
			byte[] ser = s.serialize(origin);

			// Unserialization
			ComplexTestClass generated = s.deserialize(ser);

			// Test
			compareComplexClassValues(origin, generated);
		} catch (SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}

}
