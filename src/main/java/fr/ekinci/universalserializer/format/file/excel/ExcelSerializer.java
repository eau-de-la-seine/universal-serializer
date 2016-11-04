package fr.ekinci.universalserializer.format.file.excel;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.file.excel.exception.ExcelSerializerException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.AbstractFileSerializer;
import fr.ekinci.universalserializer.format.file.FileOptions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.*;

/**
 * Excel serialization
 *
 * @author Gokan EKINCI
 */
public class ExcelSerializer<T> extends AbstractFileSerializer<T> {
	private final Map<Class<?>, Method> cellGetterMethodMap = new HashMap<>();
	private final Map<Class<?>, Method> cellSetterMethodMap = new HashMap<>();

	public ExcelSerializer(Class<T> clazz) throws ExcelSerializerException {
		this(clazz, FileOptions.builder().build());
	}

	public ExcelSerializer(Class<T> clazz, FileOptions options) throws ExcelSerializerException {
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

		try {
			initCellGetterMethodMap(this.cellGetterMethodMap);
			initCellSetterMethodMap(this.cellSetterMethodMap);
		} catch (NoSuchMethodException e) {
			throw new ExcelSerializerException(e);
		}
	}

	/**
	 * Date format style
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

	/**
	 * Get default impl (used for writing)
	 *
	 * @return
	 * @throws IOException
	 */
	private Workbook getWoorkbookImpl() throws IOException {
		return (options.excelFormat() == ExcelFormat.XLS) ? new HSSFWorkbook() : new XSSFWorkbook();
	}

	/**
	 * Get impl with {@link InputStream} parameter (used for reading)
	 *
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private Workbook getWoorkbookImpl(InputStream inputStream) throws IOException {
		return (options.excelFormat() == ExcelFormat.XLS) ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
	}

	@Override
	public Path serialize(List<T> objectToSerialize) throws SerializationException {
		try {
			final Path path = (options.destinationPath() != null) ?
					Paths.get(options.destinationPath()) :
					Files.createTempFile(null, null, new FileAttribute[0]);

			try (OutputStream outputStream = Files.newOutputStream(path)) {
				transferTo(objectToSerialize, outputStream);
			}

			return path;
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public <USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(Path objectToUnserialize) throws UnserializationException {
		try (
				InputStream inputStream = Files.newInputStream(objectToUnserialize);
				Workbook workbook = getWoorkbookImpl(inputStream)
		) {
			final Sheet sheet = workbook.getSheetAt(options.sheetIndex());
			final Iterator<Row> it = sheet.rowIterator();
			final Method addToListMethod = List.class.getMethod("add", Object.class); // For performance issues, declare once
			final USER_DEFINED_TYPE result = (USER_DEFINED_TYPE) new ArrayList<>();

			// handling header
			if (options.hasHeader() && it.hasNext()) {
				it.next();
			}
			while (it.hasNext()) {
				final Row row = it.next();
				final T tuple = clazz.newInstance();
				for (int i = 0; i < nbColumns; i++) {
					setObjectFieldValue(tuple, i, row.getCell(i));
				}
				addToListMethod.invoke(result, tuple);
			}
			return result;
		} catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new UnserializationException(e);
		}
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
			for (int i = 0, max = objectToTransfer.size(); i < max; i++) {
				final Row row = sheet.createRow(i + headerFlag);
				final T elementToTransfer = objectToTransfer.get(i);
				for (int j = 0; j < nbColumns; j++) {
					setCellValue(elementToTransfer, j, row.createCell(j), dateFormatStyle);
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
	 * @param cell
	 * @param dateFormatStyle
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private void setCellValue(
			T objectToTransfer,
			int fieldIndex,
			Cell cell,
			CellStyle dateFormatStyle
	) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		final Field field = requiredFields.get(fieldIndex);
		final Object objectToTransferFieldValue = field.get(objectToTransfer);
		if (objectToTransferFieldValue != null) {
			final Method cellSetterMethod = getCellSetterMethod(field.getType(), cell, dateFormatStyle);
			cellSetterMethod.invoke(cell, objectToTransferFieldValue);
		}
	}

	/**
	 * Return adapted method for setting cell value
	 * boolean, Date, String, double (all number types will be `double`)
	 *
	 * @param fieldClass
	 * @param cell            This parameter is required for date style
	 * @param dateFormatStyle This parameter is required for date style
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getCellSetterMethod(
			Class<?> fieldClass,
			Cell cell,
			CellStyle dateFormatStyle
	) throws NoSuchMethodException {
		if (fieldClass == Date.class) {
			cell.setCellStyle(dateFormatStyle);
		}

		return this.getCellSetterMethod(fieldClass);
	}


	/**
	 * Setting object field value
	 *
	 * @param tuple
	 * @param fieldIndex
	 * @param cell
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void setObjectFieldValue(T tuple, int fieldIndex, Cell cell) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		final Field field = requiredFields.get(fieldIndex);
		final Class<?> fieldType = field.getType();
		final Object cellValue = this.getCellGetterMethod(fieldType).invoke(cell);
		if (cellValue != null) {
			field.set(tuple, castValue(fieldType, cellValue));
		}
	}

	/**
	 * Explicit cast to number native type if necessary
	 *
	 * @param fieldClass
	 * @param value
	 * @return
	 */
	private Object castValue(Class<?> fieldClass, Object value) {
		if (value instanceof Number) {
			final Number numberValue = (Number) value;
			if (fieldClass == int.class || fieldClass == Integer.class) {
				return numberValue.intValue();
			} else if (fieldClass == long.class || fieldClass == Long.class) {
				return numberValue.longValue();
			} else if (fieldClass == float.class || fieldClass == Float.class) {
				return numberValue.floatValue();
			} else if (fieldClass == short.class || fieldClass == Short.class) {
				return numberValue.shortValue();
			} else if (fieldClass == byte.class || fieldClass == Byte.class) {
				return numberValue.byteValue();
			} else { // double or Double
				return value;
			}
		} else {
			return value;
		}
	}

	private Method getCellSetterMethod(Class<?> fieldClass) {
		final Method result = this.cellSetterMethodMap.get(fieldClass);
		return (result != null) ? result : this.cellSetterMethodMap.get(null);
	}

	private Method getCellGetterMethod(Class<?> fieldClass) {
		final Method result = this.cellGetterMethodMap.get(fieldClass);
		return (result != null) ? result : this.cellGetterMethodMap.get(null);
	}

	private void initCellGetterMethodMap(Map<Class<?>, Method> cellGetterMethodMap) throws NoSuchMethodException {
		final Class<Cell> cellClass = Cell.class;
		cellGetterMethodMap.put(String.class, cellClass.getMethod("getStringCellValue"));
		cellGetterMethodMap.put(Date.class, cellClass.getMethod("getDateCellValue"));
		final Method getBooleanCellValueMethod = cellClass.getMethod("getBooleanCellValue");
		cellGetterMethodMap.put(boolean.class, getBooleanCellValueMethod);
		cellGetterMethodMap.put(Boolean.class, getBooleanCellValueMethod);
		// Default
		cellGetterMethodMap.put(null, cellClass.getMethod("getNumericCellValue"));
	}

	private void initCellSetterMethodMap(Map<Class<?>, Method> cellSetterMethodMap) throws NoSuchMethodException {
		final Class<Cell> cellClass = Cell.class;
		cellSetterMethodMap.put(String.class, cellClass.getMethod("setCellValue", String.class));
		cellSetterMethodMap.put(Date.class, cellClass.getMethod("setCellValue", Date.class));
		final Method setBooleanCellValueMethod = cellClass.getMethod("setCellValue", boolean.class);
		cellSetterMethodMap.put(boolean.class, setBooleanCellValueMethod);
		cellSetterMethodMap.put(Boolean.class, setBooleanCellValueMethod);
		// Default
		cellSetterMethodMap.put(null, cellClass.getMethod("setCellValue", double.class));
	}
}
