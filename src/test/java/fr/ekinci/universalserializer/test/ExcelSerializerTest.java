package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.FileOptions;
import fr.ekinci.universalserializer.format.file.excel.ExcelSerializer;
import fr.ekinci.universalserializer.format.file.excel.ExcelType;
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
    @Test
    public void testSerializeAndUnserialize(){
        ExcelSerializer<ExcelFileTestClass> excelSerializer = new ExcelSerializer<>(
            ExcelFileTestClass.class,
            ExcelType.XLSX,
            FileOptions.builder().destinationPath("C:\\Temp\\tempFile").build()
        );

        final List<ExcelFileTestClass> list = new ArrayList<>();
        ExcelFileTestClass eftc = new ExcelFileTestClass();
        eftc.setAttr6((byte) 123);
        eftc.setAttr16(99d);
        list.add(eftc);
        list.add(eftc);
        list.add(eftc);
        list.add(eftc);
        list.add(eftc);

        try {
            final Path path = excelSerializer.serialize(list);
            final List<ExcelFileTestClass> unserialized = excelSerializer.unserialize(path);
            Assert.assertEquals(5, unserialized.size());
            ExcelFileTestClass o = unserialized.get(0);
            Assert.assertEquals((Byte) (byte) 123, o.getAttr6());

        } catch (SerializationException e) {
            e.printStackTrace();
        } catch (UnserializationException e) {
            e.printStackTrace();
        }

    }
}
