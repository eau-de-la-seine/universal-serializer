package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.format.file.csv.CsvSerializer;
import fr.ekinci.universalserializer.test.pojo.CsvFileTestClass;
import org.junit.Test;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;


/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class CsvSerializerTest {

	@Test
	public void testSerializeAndDeserialize() {
		CsvFileTestClass element = instanciateAndInitializeCsvFileClass();
		List<CsvFileTestClass> origin = Arrays.asList(element, element, element, element, element);

		try {
			CsvSerializer<CsvFileTestClass> s = new CsvSerializer<>(CsvFileTestClass.class);

			// Serialization
			Path ser = s.serialize(origin);

			// Unserialization
			List<CsvFileTestClass> generated = s.deserialize(ser);

			// Test
			compareCsvFileClassValues(origin, generated);
		} catch (SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}
}
