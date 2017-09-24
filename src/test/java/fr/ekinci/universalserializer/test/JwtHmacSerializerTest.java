package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.jwt.JwtHmacSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;
import fr.ekinci.universalserializer.format.text.jwt.Algorithm;
import fr.ekinci.universalserializer.format.text.jwt.exception.JwtSerializerException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;

import java.nio.charset.StandardCharsets;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JwtHmacSerializerTest {
	public final static byte[] SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87".getBytes(StandardCharsets.UTF_8);

	@Test
	public void testSerializeAndDeserialize() {
		try {
			JwtHmacSerializer<ComplexTestClass> s = new JwtHmacSerializer<>(ComplexTestClass.class, Algorithm.HS256, SECRET);
			ComplexTestClass origin = instanciateAndInitializeComplexClass();

			// SIGN
			String ser = s.serialize(origin);

			// VERIFY
			ComplexTestClass generated = s.deserialize(ser);

			// Test
			compareComplexClassValues(origin, generated);
		} catch (SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}

}
