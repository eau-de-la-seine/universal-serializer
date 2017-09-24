package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.jwt.JwtRsaSerializer;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static fr.ekinci.universalserializer.test.utils.TestClassUtils.compareComplexClassValues;
import static fr.ekinci.universalserializer.test.utils.TestClassUtils.instanciateAndInitializeComplexClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class JwtRsaSerializerTest {

	@Test
	public void testSerializeAndDeserialize() throws NoSuchAlgorithmException {
		try {
			final KeyPair keyPair = getKeyPair(1024); // Tested with 512, and "key is too short for this signature algorithm"
			JwtRsaSerializer<ComplexTestClass> s = new JwtRsaSerializer<>(
				ComplexTestClass.class,
				"SHA512withRSA",
				keyPair.getPrivate(),
				keyPair.getPublic()
			);

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

	private KeyPair getKeyPair(int keySize) throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(keySize);
		return keyGen.genKeyPair();
	}

}
