package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.format.text.xml.XmlSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.TestClassUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class XmlSerializerTest {

	@Test
	public void testSerializeAndDeserialize() {
		XmlSerializer<ComplexTestClass> s = new XmlSerializer<>(ComplexTestClass.class);
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
