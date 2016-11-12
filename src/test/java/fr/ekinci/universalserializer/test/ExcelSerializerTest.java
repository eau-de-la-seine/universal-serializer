package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.excel.exception.ExcelSerializerException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.format.file.excel.ExcelSerializer;
import fr.ekinci.universalserializer.test.pojo.ExcelFileTestClass;
import org.junit.Test;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import static fr.ekinci.universalserializer.test.utils.TestClassUtils.*;

/**
 * A simple test of serialization and unserialization
 *
 * @author Gokan EKINCI
 */
public class ExcelSerializerTest {

	@Test
	public void testSerializeAndUnserialize() {
		ExcelFileTestClass element = instanciateAndInitializeExcelFileClass();
		List<ExcelFileTestClass> origin = Arrays.asList(element, element, element, element, element);

		try {
			ExcelSerializer<ExcelFileTestClass> s = new ExcelSerializer<>(ExcelFileTestClass.class);

			// Serialization
			Path ser = s.serialize(origin);

			// Unserialization
			List<ExcelFileTestClass> generated = s.deserialize(ser);

			// Test
			compareExcelFileClassValues(origin, generated);
		} catch (ExcelSerializerException | SerializationException | DeserializationException e) {
			e.printStackTrace();
		}
	}
}
