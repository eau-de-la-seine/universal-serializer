package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.format.text.base64.AbstractBase64Serializer;
import fr.ekinci.universalserializer.format.text.base64.Base64UrlSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import org.junit.Test;
import static fr.ekinci.universalserializer.test.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class Base64UrlSerializerTest {

    @Test
    public void testSerializeAndUnserialize(){
        AbstractBase64Serializer s = new Base64UrlSerializer();
        ComplexTestClass origin = instanciateAndInitializeComplexClass();

        try {
            // Serialization
            String ser = s.serialize(origin);

            // Unserialization
            ComplexTestClass generated = s.unserialize(ser);

            compareComplexClassValues(origin, generated);
        } catch (SerializationException | UnserializationException e) {
            e.printStackTrace();
        }
    }
}
