package fr.ekinci.universalserializer.test.utils;

import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import fr.ekinci.universalserializer.test.pojo.CsvFileTestClass;
import fr.ekinci.universalserializer.test.pojo.ExcelFileTestClass;
import fr.ekinci.universalserializer.test.pojo.SimpleTestClass;
import fr.ekinci.universalserializer.test.protocolbuffers.ProtoComplexTestClass;
import fr.ekinci.universalserializer.test.thrift.ThriftComplexTestClass;
import org.junit.Assert;

import java.util.List;

/**
 * @author Gokan EKINCI
 */
public class CompareTestUtils {

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

	/*---------------------------------*/
	/* --- GOOGLE PROTOCOL BUFFERS --- */
	/*---------------------------------*/

	public static void compareSimpleClassValues(ProtoComplexTestClass.ComplexTestClass origin, ProtoComplexTestClass.ComplexTestClass generated) {
		Assert.assertEquals(origin.getAttr1(), generated.getAttr1());
		Assert.assertEquals(origin.getAttr2(), generated.getAttr2());
		Assert.assertEquals(origin.getAttr3(), generated.getAttr3());
		Assert.assertEquals(origin.getAttr4(), generated.getAttr4());
		Assert.assertEquals(origin.getAttr5(), generated.getAttr5(), 0.001f);
		Assert.assertEquals(origin.getAttr6(), generated.getAttr6(), 0.001d);
		Assert.assertEquals(origin.getAttr7(), generated.getAttr7());
		Assert.assertEquals(origin.getAttr8(), generated.getAttr8());
		Assert.assertEquals(origin.getAttr9(), generated.getAttr9());
	}

	public static void compareComplexClassValues(ProtoComplexTestClass.ComplexTestClass origin, ProtoComplexTestClass.ComplexTestClass generated) {
		compareSimpleClassValues(origin, generated);

		// Complex attributes comparison
		final List<Double> originDoubleList = origin.getAttr10List();
		final List<Double> generatedDoubleList = generated.getAttr10List();
		Assert.assertEquals(originDoubleList.size(), generatedDoubleList.size());
		for (int i = 0, max = originDoubleList.size(); i < max; i++) {
			Assert.assertEquals(originDoubleList.get(i), generatedDoubleList.get(i), 0.001d);
		}

		final List<String> originStringList = origin.getAttr11List();
		final List<String> generatedStringList = generated.getAttr11List();
		Assert.assertEquals(originStringList.size(), generatedStringList.size());
		for (int i = 0, max = originStringList.size(); i < max; i++) {
			Assert.assertEquals(originStringList.get(i), generatedStringList.get(i));
		}

		compareSimpleClassValues(origin.getAttr12(), generated.getAttr12());

		final List<ProtoComplexTestClass.ComplexTestClass> originCtcList = origin.getAttr13List();
		final List<ProtoComplexTestClass.ComplexTestClass> generatedCtcList = generated.getAttr13List();
		Assert.assertEquals(originCtcList.size(), generatedCtcList.size());
		for (int i = 0, max = originCtcList.size(); i < max; i++) {
			compareSimpleClassValues(originCtcList.get(i), generatedCtcList.get(i));
		}
	}

	/*-----------------------*/
	/* --- APACHE THRIFT --- */
	/*-----------------------*/

	public static void compareSimpleClassValues(ThriftComplexTestClass origin, ThriftComplexTestClass generated) {
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

	public static void compareComplexClassValues(ThriftComplexTestClass origin, ThriftComplexTestClass generated) {
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

		final List<ThriftComplexTestClass> originCtcList = origin.getAttr13();
		final List<ThriftComplexTestClass> generatedCtcList = generated.getAttr13();
		Assert.assertEquals(originCtcList.size(), generatedCtcList.size());
		for (int i = 0, max = originCtcList.size(); i < max; i++) {
			compareSimpleClassValues(originCtcList.get(i), generatedCtcList.get(i));
		}
	}

	/*---------------------*/
	/* --- EXCEL FILES --- */
	/*---------------------*/

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

	/*-------------------*/
	/* --- CSV FILES --- */
	/*-------------------*/

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
