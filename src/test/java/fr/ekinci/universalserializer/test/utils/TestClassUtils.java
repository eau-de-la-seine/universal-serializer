package fr.ekinci.universalserializer.test.utils;

import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import fr.ekinci.universalserializer.test.pojo.CsvFileTestClass;
import fr.ekinci.universalserializer.test.pojo.ExcelFileTestClass;
import fr.ekinci.universalserializer.test.pojo.SimpleTestClass;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
public class TestClassUtils {

	public static SimpleTestClass instanciateAndInitializeSimpleClass() {
		final SimpleTestClass obj = new SimpleTestClass();
		obj.setAttr1((byte) 1);
		obj.setAttr2((short) 2);
		obj.setAttr3(3);
		obj.setAttr4(4L);
		obj.setAttr5(5f);
		obj.setAttr6(6d);
		obj.setAttr7(true);
		obj.setAttr8('8');
		obj.setAttr9("9");
		return obj;
	}

	public static ComplexTestClass instanciateAndInitializeComplexClass() {
		final ComplexTestClass obj = new ComplexTestClass();
		obj.setAttr1((byte) 1);
		obj.setAttr2((short) 2);
		obj.setAttr3(3);
		obj.setAttr4(4L);
		obj.setAttr5(5f);
		obj.setAttr6(6d);
		obj.setAttr7(true);
		obj.setAttr8('8');
		obj.setAttr9("9");

		// Complex attributes initialization
		obj.setAttr10(new ArrayList<Double>() {{
			add(10.1d);
			add(10.2d);
			add(10.3d);
		}});

		obj.setAttr11(new ArrayList<String>() {{
			add("11.1");
			add("11.2");
			add("11.3");
		}});

		obj.setAttr12(new ComplexTestClass());

		obj.setAttr13(new ArrayList<ComplexTestClass>() {{
			add(new ComplexTestClass());
			add(new ComplexTestClass());
			add(new ComplexTestClass());
		}});

		return obj;
	}

	public static ExcelFileTestClass instanciateAndInitializeExcelFileClass() {
		final ExcelFileTestClass obj = new ExcelFileTestClass();
		obj.setAttr0("0");
		obj.setAttr1("1");
		obj.setAttr2(new Date());
		obj.setAttr3(true);
		obj.setAttr4(true);
		obj.setAttr5((byte) 5);
		obj.setAttr6((byte) 6);
		obj.setAttr7((short) 7);
		obj.setAttr8((short) 8);
		obj.setAttr9(9);
		obj.setAttr10(10);
		obj.setAttr11(11L);
		obj.setAttr12(12L);
		obj.setAttr13(13F);
		obj.setAttr14(14F);
		obj.setAttr15(15d);
		obj.setAttr16(16d);
		return obj;
	}

	public static CsvFileTestClass instanciateAndInitializeCsvFileClass() {
		final CsvFileTestClass obj = new CsvFileTestClass();
		obj.setAttr0("0");
		obj.setAttr1("1");
		obj.setAttr2(new Date());
		obj.setAttr3(true);
		obj.setAttr4(true);
		obj.setAttr5((byte) 5);
		obj.setAttr6((byte) 6);
		obj.setAttr7((short) 7);
		obj.setAttr8((short) 8);
		obj.setAttr9(9);
		obj.setAttr10(10);
		obj.setAttr11(11L);
		obj.setAttr12(12L);
		obj.setAttr13(13F);
		obj.setAttr14(14F);
		obj.setAttr15(15d);
		obj.setAttr16(16d);
//		obj.setAttr17('A');
//		obj.setAttr18('B');
//		obj.setAttr19(LocalDate.now());
//		obj.setAttr20(LocalDateTime.now());
		return obj;
	}

	public static void compareSimpleClassValues(SimpleTestClass origin, SimpleTestClass generated) {
		Assert.assertEquals(origin.getAttr1(), generated.getAttr1());
		Assert.assertEquals(origin.getAttr2(), generated.getAttr2());
		Assert.assertEquals(origin.getAttr3(), generated.getAttr3());
		Assert.assertEquals(origin.getAttr4(), generated.getAttr4());
		Assert.assertEquals(origin.getAttr5(), generated.getAttr5(), 0.001f);
		Assert.assertEquals(origin.getAttr6(), generated.getAttr6(), 0.001d);
		Assert.assertEquals(origin.isAttr7(), generated.isAttr7());
		Assert.assertEquals(origin.getAttr8(), generated.getAttr8());
		Assert.assertEquals(origin.getAttr9(), generated.getAttr9());
	}

	public static void compareComplexClassValues(ComplexTestClass origin, ComplexTestClass generated) {
		compareSimpleClassValues(origin, generated);

		// Complex attributes comparison
		final List<Double> originDoubleList = origin.getAttr10();
		final List<Double> generatedDoubleList = generated.getAttr10();
		Assert.assertEquals(originDoubleList.size(), generatedDoubleList.size());
		for (int i = 0, max = originDoubleList.size(); i < max; i++) {
			Assert.assertEquals(originDoubleList.get(i), generatedDoubleList.get(i), 0.001d);
		}

		final List<String> originStringList = origin.getAttr11();
		final List<String> generatedStringList = generated.getAttr11();
		Assert.assertEquals(originStringList.size(), generatedStringList.size());
		for (int i = 0, max = originStringList.size(); i < max; i++) {
			Assert.assertEquals(originStringList.get(i), generatedStringList.get(i));
		}

		compareSimpleClassValues(origin.getAttr12(), generated.getAttr12());

		final List<ComplexTestClass> originCtcList = origin.getAttr13();
		final List<ComplexTestClass> generatedCtcList = generated.getAttr13();
		Assert.assertEquals(originCtcList.size(), generatedCtcList.size());
		for (int i = 0, max = originCtcList.size(); i < max; i++) {
			compareSimpleClassValues(originCtcList.get(i), generatedCtcList.get(i));
		}
	}

	public static void compareExcelFileClassValues(List<ExcelFileTestClass> originList, List<ExcelFileTestClass> generatedList) {
		Assert.assertEquals("Origin and generated does NOT have the same size", originList.size(), generatedList.size());

		for (int i = 0, size = originList.size(); i < size; i++) {
			final ExcelFileTestClass origin = originList.get(i);
			final ExcelFileTestClass generated = generatedList.get(i);

			Assert.assertEquals(origin.getAttr0(), generated.getAttr0());
			Assert.assertEquals(origin.getAttr1(), generated.getAttr1());
			Assert.assertEquals(origin.getAttr2(), generated.getAttr2());
			Assert.assertEquals(origin.isAttr3(), generated.isAttr3());
			Assert.assertEquals(origin.getAttr4(), generated.getAttr4());
			Assert.assertEquals(origin.getAttr5(), generated.getAttr5());
			Assert.assertEquals(origin.getAttr6(), generated.getAttr6());
			Assert.assertEquals(origin.getAttr7(), generated.getAttr7());
			Assert.assertEquals(origin.getAttr8(), generated.getAttr8());
			Assert.assertEquals(origin.getAttr9(), generated.getAttr9());
			Assert.assertEquals(origin.getAttr10(), generated.getAttr10());
			Assert.assertEquals(origin.getAttr11(), generated.getAttr11());
			Assert.assertEquals(origin.getAttr12(), generated.getAttr12());
			Assert.assertEquals(origin.getAttr13(), generated.getAttr13(), 0.001f);
			Assert.assertEquals(origin.getAttr14(), generated.getAttr14());
			Assert.assertEquals(origin.getAttr15(), generated.getAttr15(), 0.001d);
			Assert.assertEquals(origin.getAttr16(), generated.getAttr16());
		}
	}

	public static void compareCsvFileClassValues(List<CsvFileTestClass> originList, List<CsvFileTestClass> generatedList) {
		Assert.assertEquals("Origin and generated does NOT have the same size", originList.size(), generatedList.size());

		for (int i = 0, size = originList.size(); i < size; i++) {
			final CsvFileTestClass origin = originList.get(i);
			final CsvFileTestClass generated = generatedList.get(i);

			Assert.assertEquals(origin.getAttr0(), generated.getAttr0());
			Assert.assertEquals(origin.getAttr1(), generated.getAttr1());
			// May encounter precision problem
			// Assert.assertTrue(origin.getAttr2().compareTo(generated.getAttr2()) == 0);
			Assert.assertEquals(origin.getAttr2().toString(), generated.getAttr2().toString());
			Assert.assertEquals(origin.isAttr3(), generated.isAttr3());
			Assert.assertEquals(origin.getAttr4(), generated.getAttr4());
			Assert.assertEquals(origin.getAttr5(), generated.getAttr5());
			Assert.assertEquals(origin.getAttr6(), generated.getAttr6());
			Assert.assertEquals(origin.getAttr7(), generated.getAttr7());
			Assert.assertEquals(origin.getAttr8(), generated.getAttr8());
			Assert.assertEquals(origin.getAttr9(), generated.getAttr9());
			Assert.assertEquals(origin.getAttr10(), generated.getAttr10());
			Assert.assertEquals(origin.getAttr11(), generated.getAttr11());
			Assert.assertEquals(origin.getAttr12(), generated.getAttr12());
			Assert.assertEquals(origin.getAttr13(), generated.getAttr13(), 0.001f);
			Assert.assertEquals(origin.getAttr14(), generated.getAttr14());
			Assert.assertEquals(origin.getAttr15(), generated.getAttr15(), 0.001d);
			Assert.assertEquals(origin.getAttr16(), generated.getAttr16());
//			Assert.assertEquals(origin.getAttr17(), generated.getAttr17());
//			Assert.assertEquals(origin.getAttr18(), generated.getAttr18());
//			Assert.assertEquals(origin.getAttr19(), generated.getAttr19());
//			Assert.assertEquals(origin.getAttr20(), generated.getAttr20());
		}
	}
}
