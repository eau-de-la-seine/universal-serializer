package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.excel.exception.ExcelSerializerException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.FileOptions;
import fr.ekinci.universalserializer.format.file.excel.ExcelSerializer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
@Ignore
public class ExcelSerializerTest {
	private final int N_TIMES = 100_000;

	@Test
	public void testSerializeAndUnserialize() {
		try {
			long t0 = System.currentTimeMillis();
			final List<ExcelFileTestClass> list = new ArrayList<>();
			ExcelFileTestClass eftc = new ExcelFileTestClass();
			eftc.setAttr6((byte) 123);
			eftc.setAttr16(99d);
			for (int i = 0; i < N_TIMES; i++) {
				list.add(eftc);
			}

			long t1 = System.currentTimeMillis();
			final ExcelSerializer<ExcelFileTestClass> excelSerializer = new ExcelSerializer<>(
					ExcelFileTestClass.class,
					FileOptions.builder().build()
			);

			long t2 = System.currentTimeMillis();
			final Path path = excelSerializer.serialize(list);
			long t3 = System.currentTimeMillis();
			final List<ExcelFileTestClass> unserialized = excelSerializer.unserialize(path);
			long t4 = System.currentTimeMillis();

			System.out.println("t1 - t0 : " + (t1 - t0) / 1_000);
			System.out.println("t2 - t1 : " + (t2 - t1) / 1_000);
			System.out.println("t3 - t2 : " + (t3 - t2) / 1_000);
			System.out.println("t4 - t3 : " + (t4 - t3) / 1_000);

			Assert.assertEquals(N_TIMES, unserialized.size());
			ExcelFileTestClass o = unserialized.get(0);
			Assert.assertEquals((Byte) (byte) 123, o.getAttr6());

		} catch (ExcelSerializerException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (SerializationException e) {
			e.printStackTrace();
		} catch (UnserializationException e) {
			e.printStackTrace();
		}

	}
}
