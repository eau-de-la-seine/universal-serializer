package fr.ekinci.universalserializer.test;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.excel.ExcelSerializer;
import fr.ekinci.universalserializer.format.file.excel.ExcelType;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
            ExcelType.XLSX
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

        /*
        File file = new File("C:/Temp/newfile.xlsx");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            excelSerializer.transferTo(list, fos);
        } catch (SerializationException | IOException e) {
            e.printStackTrace();
        }
        */

    }
}
