package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.jwt.JwtSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;
import fr.ekinci.universalserializer.format.text.jwt.Algorithm;
import fr.ekinci.universalserializer.format.text.jwt.exception.JwtSerializerException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JwtSerializerTest {
	public final static String SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87";

	@Test
	public void testSerializeAndDeserialize() {
		try {
			JwtSerializer<ComplexTestClass> s = new JwtSerializer<>(Algorithm.HS256, ComplexTestClass.class, SECRET);
			ComplexTestClass origin = instanciateAndInitializeComplexClass();

			// SIGN
			String ser = s.serialize(origin);

			// VERIFY
			ComplexTestClass generated = s.deserialize(ser);

			// Test
			compareComplexClassValues(origin, generated);
		} catch (JwtSerializerException | SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}

}
