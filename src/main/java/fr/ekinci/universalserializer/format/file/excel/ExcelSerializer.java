package fr.ekinci.universalserializer.format.file.excel;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.AbstractFileSerializer;
import fr.ekinci.universalserializer.format.file.FileOptions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
public class ExcelSerializer<T> extends AbstractFileSerializer<T> {
    private final ExcelType excelType;

    public ExcelSerializer(Class<T> clazz, ExcelType excelType) {
        this(clazz, excelType, FileOptions.builder().build());
    }

    public ExcelSerializer(Class<T> clazz, ExcelType excelType, FileOptions options) {
        super(
            new Class[]{
                byte.class,
                Byte.class,
                short.class,
                Short.class,
                int.class,
                Integer.class,
                long.class,
                Long.class,
                float.class,
                Float.class,
                double.class,
                Double.class,
                String.class,
                Date.class,
                boolean.class,
                Boolean.class
            },
            clazz,
            options
        );
        this.excelType = excelType;
    }

    /**
     *
     * @param workbook
     * @return
     */
    private CellStyle getDateFormatStyle(Workbook workbook) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(
            workbook.getCreationHelper()
                .createDataFormat()
                .getFormat(options.dateFormat())
        );
        return cellStyle;
    }

    private Workbook getWoorkbookImpl() throws IOException {
        return (excelType == ExcelType.XLS) ? new HSSFWorkbook() : new XSSFWorkbook();
    }

    private Workbook getWoorkbookImpl(InputStream inputStream) throws IOException {
        return (excelType == ExcelType.XLS) ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
    }

    @Override
    public Path serialize(List<T> objectToSerialize) throws SerializationException {
        try {
            final Path tempFile = (options.destinationPath() != null) ?
                Paths.get(options.destinationPath()) :
                    Files.createTempFile(null, null, null);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                transferTo(objectToSerialize, baos);
                Files.write(tempFile, baos.toByteArray());
                return tempFile;
            }
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <J> J unserialize(Path objectToUnserialize) throws UnserializationException {
        return null;
    }

    @Override
    public void transferTo(List<T> objectToTransfer, OutputStream outputStream) throws SerializationException {
        try (Workbook wb = getWoorkbookImpl()) {
            final Sheet sheet = wb.createSheet();
            // handling header
            if (options.hasHeader()) {
                final Row headerRow = sheet.createRow(0);
                for (int i = 0; i < nbColumns; i++) {
                    final Cell cell = headerRow.createCell(i);
                    cell.setCellValue(fileInfo.headerColumnNames()[i]);
                }
            }

            final int headerFlag = (options.hasHeader()) ? 1 : 0;
            final CellStyle dateFormatStyle = getDateFormatStyle(wb);
            final Class<Cell> cellClass = Cell.class; // For performance issues, declare once
            for (int i = 0, max = objectToTransfer.size(); i < max; i++) {
                final Row row = sheet.createRow(i + headerFlag);
                final T elementToTransfer = objectToTransfer.get(i);
                for (int j = 0; j < nbColumns; j++) {
                    setCellValue(elementToTransfer, j, cellClass, row.createCell(j), dateFormatStyle);
                }
            }

            wb.write(outputStream);
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SerializationException(e);
        }
    }

    /**
     * Setting cell value
     *
     * @param objectToTransfer
     * @param fieldIndex
     * @param cellClass
     * @param cell
     * @param dateFormatStyle
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private void setCellValue(
        T objectToTransfer,
        int fieldIndex,
        Class<Cell> cellClass,
        Cell cell,
        CellStyle dateFormatStyle
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final Field field = requiredFields.get(fieldIndex);
        field.setAccessible(true);
        final Object objectToTransferFieldValue = field.get(objectToTransfer);
        if (objectToTransferFieldValue != null) {
            final Method cellSetterMethod = getCellSetterMethod(field.getType(), cellClass, cell, dateFormatStyle);
            cellSetterMethod.invoke(cell, objectToTransferFieldValue);
        }
    }

    /**
     * Return adapted method for setting cell value
     * boolean, Date, String, double (all number types will be `double`)
     *
     * @param fieldClass
     * @param cellClass
     * @param cell             This parameter is required for date style
     * @param dateFormatStyle  This parameter is required for date style
     * @return
     * @throws NoSuchMethodException
     */
    private Method getCellSetterMethod(
        Class<?> fieldClass,
        Class<Cell> cellClass,
        Cell cell,
        CellStyle dateFormatStyle
    ) throws NoSuchMethodException {
        if(fieldClass == String.class) {
            return cellClass.getMethod("setCellValue", String.class);
        } else if(fieldClass == Date.class) {
            cell.setCellStyle(dateFormatStyle);
            return cellClass.getMethod("setCellValue", Date.class);
        } else if (fieldClass == boolean.class || fieldClass == Boolean.class) {
            return cellClass.getMethod("setCellValue", boolean.class);
        } else {
            // For all other number primitives (short, int etc...)
            return cellClass.getMethod("setCellValue", double.class);
        }
    }
}
