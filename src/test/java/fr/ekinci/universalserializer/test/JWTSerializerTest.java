package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.jwt.JwtSerializer;
import org.junit.Test;
import fr.ekinci.universalserializer.format.text.jwt.Algorithm;
import fr.ekinci.universalserializer.format.text.jwt.exception.JwtSerializerException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

import static fr.ekinci.universalserializer.test.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class JwtSerializerTest {
	public final static String SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87";

	@Test
	public void testSerializeAndUnserialize() {
		try {
			JwtSerializer s = new JwtSerializer(Algorithm.HS256, ComplexTestClass.class, SECRET);
			ComplexTestClass origin = instanciateAndInitializeComplexClass();

			// SIGN
			String ser = s.serialize(origin);

			// VERIFY
			ComplexTestClass generated = s.unserialize(ser);
			compareComplexClassValues(origin, generated);
		} catch (JwtSerializerException | SerializationException | UnserializationException e) {
			e.printStackTrace();
		}
	}

}
