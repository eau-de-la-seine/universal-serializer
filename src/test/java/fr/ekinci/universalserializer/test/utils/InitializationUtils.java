package fr.ekinci.universalserializer.test.utils;

import com.google.protobuf.ByteString;
import fr.ekinci.universalserializer.test.pojo.ComplexTestClass;
import fr.ekinci.universalserializer.test.pojo.CsvFileTestClass;
import fr.ekinci.universalserializer.test.pojo.ExcelFileTestClass;
import fr.ekinci.universalserializer.test.protocolbuffers.ProtoComplexTestClass;
import fr.ekinci.universalserializer.test.thrift.ThriftComplexTestClass;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Gokan EKINCI
 */
public class InitializationUtils {
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
		obj.setAttr9("&é'(-è_çà)=€$");

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

	public static ProtoComplexTestClass.ComplexTestClass instanciateAndInitializeProtoComplexClass() {
		final ProtoComplexTestClass.ComplexTestClass obj = ProtoComplexTestClass.ComplexTestClass.newBuilder()
			.setAttr1(ByteString.copyFrom(new byte[]{1}))
			.setAttr2((short) 2)
			.setAttr3(3)
			.setAttr4(4L)
			.setAttr5(5f)
			.setAttr6(6d)
			.setAttr7(true)
			.setAttr8("8")
			.setAttr9("&é'(-è_çà)=€$")

			// Complex attributes initialization
			.addAllAttr10(new ArrayList<Double>() {{
				add(10.1d);
				add(10.2d);
				add(10.3d);
			}})

			.addAllAttr11(new ArrayList<String>() {{
				add("11.1");
				add("11.2");
				add("11.3");
			}})

			.setAttr12(ProtoComplexTestClass.ComplexTestClass.newBuilder().build())

			.addAllAttr13(new ArrayList<ProtoComplexTestClass.ComplexTestClass>() {{
				add(ProtoComplexTestClass.ComplexTestClass.newBuilder().build());
				add(ProtoComplexTestClass.ComplexTestClass.newBuilder().build());
				add(ProtoComplexTestClass.ComplexTestClass.newBuilder().build());
			}})
			.build();

		return obj;
	}

	public static ThriftComplexTestClass instanciateAndInitializeThriftComplexClass() {
		final ThriftComplexTestClass obj = new ThriftComplexTestClass();
		obj.setAttr1((byte) 1);
		obj.setAttr2((short) 2);
		obj.setAttr3(3);
		obj.setAttr4(4L);
		obj.setAttr5(5f);
		obj.setAttr6(6d);
		obj.setAttr7(true);
		obj.setAttr8("8");
		obj.setAttr9("&é'(-è_çà)=€$");

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

		obj.setAttr12(new ThriftComplexTestClass());

		obj.setAttr13(new ArrayList<ThriftComplexTestClass>() {{
			add(new ThriftComplexTestClass());
			add(new ThriftComplexTestClass());
			add(new ThriftComplexTestClass());
		}});

		return obj;
	}

	public static ExcelFileTestClass instanciateAndInitializeExcelFileClass() {
		final ExcelFileTestClass obj = new ExcelFileTestClass();
		obj.setAttr0("&é'(-è_çà)=€$");
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
		obj.setAttr0("&é'(-è_çà)=€$");
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
}
