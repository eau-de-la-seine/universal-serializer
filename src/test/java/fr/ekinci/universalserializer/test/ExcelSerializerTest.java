package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.excel.ExcelSerializer;
import fr.ekinci.universalserializer.format.file.excel.exception.ExcelSerializerException;
import fr.ekinci.universalserializer.format.file.exception.AbstractFileSerializerException;
import fr.ekinci.universalserializer.test.pojo.ExcelFileTestClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static fr.ekinci.universalserializer.test.utils.CompareTestUtils.compareExcelFileClassValues;
import static fr.ekinci.universalserializer.test.utils.InitializationUtils.instanciateAndInitializeExcelFileClass;

/**
 * A simple test of serialization and deserialization
 *
 * @author Gokan EKINCI
 */
public class ExcelSerializerTest {
	private static final ExcelFileTestClass element = instanciateAndInitializeExcelFileClass();
	private static final List<ExcelFileTestClass> origin = Arrays.asList(element, element, element, element, element);
	private static ExcelSerializer<ExcelFileTestClass> s;

	@BeforeClass
	public static void staticInit() throws AbstractFileSerializerException {
		s = new ExcelSerializer<>(ExcelFileTestClass.class);
	}

	@Test
	public void testSerializeAndDeserialize() throws AbstractFileSerializerException, ExcelSerializerException, SerializationException, DeserializationException {
		// Serialization
		Path ser = s.serialize(origin);

		// Unserialization
		List<ExcelFileTestClass> generated = s.deserialize(ser);

		// Test
		compareExcelFileClassValues(origin, generated);
	}

	@Test
	public void testSerializeAndDeserialize_stream() throws AbstractFileSerializerException, SerializationException, DeserializationException, ExcelSerializerException, IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// Serialization
			s.sendTo(origin, out);

			// Unserialization
			try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
				List<ExcelFileTestClass> generated = s.receiveFrom(in);

				// Test
				compareExcelFileClassValues(origin, generated);
			}
		}
	}
}
