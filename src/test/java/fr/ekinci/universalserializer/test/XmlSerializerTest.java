package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.text.xml.XmlSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.TestClassUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class XmlSerializerTest {

	@Test
	public void testSerializeAndUnserialize() {
		XmlSerializer s = new XmlSerializer(ComplexTestClass.class);
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
