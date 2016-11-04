package fr.ekinci.universalserializer.test;

import org.junit.Test;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

import static fr.ekinci.universalserializer.test.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class JavaSerializerTest {

	@Test
	public void testSerializeAndUnserialize() {
		JavaSerializer s = new JavaSerializer();
		ComplexTestClass origin = instanciateAndInitializeComplexClass();

		try {
			// Serialization
			byte[] ser = s.serialize(origin);

			// Unserialization
			ComplexTestClass generated = s.unserialize(ser);

			compareComplexClassValues(origin, generated);
		} catch (SerializationException | UnserializationException e) {
			e.printStackTrace();
		}
	}

}
