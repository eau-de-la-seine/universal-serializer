package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.jwt.JwtRsaSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JwtRsaSerializerTest {
	private static JwtRsaSerializer<ComplexTestClass> s;
	private static final ComplexTestClass origin = instanciateAndInitializeComplexClass();

	@BeforeClass
	public static void staticInit() throws NoSuchAlgorithmException {
		final KeyPair keyPair = getKeyPair(1024); // Tested with 512, and "key is too short for this signature algorithm"
		s = new JwtRsaSerializer<>(
				ComplexTestClass.class,
				"SHA512withRSA",
				keyPair.getPrivate(),
				keyPair.getPublic()
		);
	}

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException {
		// Serialization (SIGN)
		String ser = s.serialize(origin);

		// Unserialization (VERIFY)
		ComplexTestClass generated = s.deserialize(ser);

		// Test
		compareComplexClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws SerializationException, DeserializationException, IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization (SIGN)
			s.sendTo(origin, out);

			// Unserialization (VERIFY)
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				ComplexTestClass generated = s.receiveFrom(in);

				// Test
				compareComplexClassValues(origin, generated);
			}
		}
	}

	private static KeyPair getKeyPair(int keySize) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(keySize);
		return keyGen.genKeyPair();
	}

}
