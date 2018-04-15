package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.csv.CsvSerializer;
import fr.ekinci.universalserializer.format.file.exception.AbstractFileSerializerException;
import fr.ekinci.universalserializer.test.pojo.CsvFileTestClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareCsvFileClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeCsvFileClass;


/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class CsvSerializerTest {
	private static final CsvFileTestClass element = instanciateAndInitializeCsvFileClass();
	private static final List<CsvFileTestClass> origin = Arrays.asList(element, element, element, element, element);
	private static CsvSerializer<CsvFileTestClass> s;

	@BeforeClass
	public static void staticInit() throws AbstractFileSerializerException {
		s = new CsvSerializer<>(CsvFileTestClass.class);
	}

	@Test
	public void testSerializeAndDeserialize() throws SerializationException, DeserializationException{
		// Serialization
		Path ser = s.serialize(origin);

		// Unserialization
		List<CsvFileTestClass> generated = s.deserialize(ser);

		// Test
		compareCsvFileClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws SerializationException, DeserializationException, IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			s.sendTo(origin, out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				List<CsvFileTestClass> generated = s.receiveFrom(in);

				// Test
				compareCsvFileClassValues(origin, generated);
			}
		}
	}
}
