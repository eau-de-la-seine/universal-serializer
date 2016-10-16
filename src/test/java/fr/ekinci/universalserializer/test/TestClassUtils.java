package fr.ekinci.universalserializer.test;

import org.junit.Assert;
import java.util.ArrayList;
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
        obj.setAttr10(new ArrayList<Double>(){{
            add(10.1d);
            add(10.2d);
            add(10.3d);
        }});

        obj.setAttr11(new ArrayList<String>(){{
            add("11.1");
            add("11.2");
            add("11.3");
        }});

        obj.setAttr12(new ComplexTestClass());

        obj.setAttr13(new ArrayList<ComplexTestClass>(){{
            add(new ComplexTestClass());
            add(new ComplexTestClass());
            add(new ComplexTestClass());
        }});

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
}
