package fr.ekinci.universalserializer.format.file.csv;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.AbstractFileSerializer;
import fr.ekinci.universalserializer.format.file.FileOptions;
import fr.ekinci.universalserializer.format.file.FileSerializerUtils;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * CSV serialization
 *
 * 16 types are supported, see {@link CsvSerializer#CsvSerializer(Class, FileOptions)}
 * Apache doc : https://commons.apache.org/proper/commons-csv/user-guide.html
 *
 * @author Gokan EKINCI
 */
public class CsvSerializer<T> extends AbstractFileSerializer<T> {

	public CsvSerializer(Class<T> clazz) {
		this(clazz, FileOptions.builder().build());
	}

	public CsvSerializer(Class<T> clazz, FileOptions options) {
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
						Boolean.class,
						// LocalDate.class,
						// LocalDateTime.class,
						// char.class,
						// Character.class
				},
				clazz,
				options
		);
	}

	@Override
	public Path serialize(List<T> objectToSerialize) throws SerializationException {
		return FileSerializerUtils.defaultSerialize(this, objectToSerialize, options.destinationPath());
	}

	@Override
	public <USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(Path objectToUnserialize) throws UnserializationException {
		final USER_DEFINED_TYPE result = (USER_DEFINED_TYPE) new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(objectToUnserialize)) {
			final Method addToListMethod = List.class.getMethod("add", Object.class); // For performance issues, declare once
			final Iterator<CSVRecord> iterator = options.csvFormat().parse(reader).iterator();

			// handling header
			if (options.hasHeader() && iterator.hasNext()) {
				iterator.next();
			}

			while (iterator.hasNext()) {
				final CSVRecord csvRecord = iterator.next();
				final T tuple = clazz.newInstance();
				for (int i = 0; i < nbColumns; i++) {
					setObjectFieldValue(tuple, i, csvRecord.get(i));
				}
				addToListMethod.invoke(result, tuple);
			}

		} catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | ParseException e) {
			throw new UnserializationException(e);
		}

		return result;
	}

	/**
	 * We won't close {@link PrintStream} or {@link CSVPrinter} because they close the outputStream
	 *
	 * @param objectToTransfer
	 * @param outputStream
	 * @throws SerializationException
	 */
	@Override
	public void transferTo(List<T> objectToTransfer, OutputStream outputStream) throws SerializationException {
		try {
			final CSVPrinter csvPrinter = options.csvFormat().print(new PrintStream(outputStream));

			// handling header
			if (options.hasHeader()) {
				csvPrinter.printRecord(fileInfo.headerColumnNames());
			}

			for (T tuple : objectToTransfer) {
				final String[] values = new String[nbColumns];
				for (int i = 0; i < nbColumns; i++) {
					final Field field = requiredFields.get(i);
					values[i] = objectToString(field.get(tuple), field);
				}
				csvPrinter.printRecord(values);
			}

			// Flush the printStream
			csvPrinter.flush();
		} catch (IOException  | IllegalAccessException e) {
			throw new SerializationException(e);
		}
	}

	/**
	 * Transform string value to object and set object value to tuple's field
	 * Used for serialization
	 *
	 * @param tuple
	 * @param fieldIndex
	 * @param stringValue
	 * @throws ParseException
	 * @throws IllegalAccessException
	 */
	private void setObjectFieldValue(T tuple, int fieldIndex, String stringValue) throws ParseException, IllegalAccessException {
		final Field field = requiredFields.get(fieldIndex);
		final Object fieldValue = stringToObject(stringValue, field);
		field.set(tuple, fieldValue);
	}

	/**
	 * Convert a string to object by using field type
	 * Used for serialization
	 *
	 * @param string     String to transform
	 * @param field      Field used to retrieve type
	 * @return converted value from string
	 */
	private Object stringToObject(String string, Field field) throws ParseException {
		final Class<?> clazz = field.getType();

		if (clazz == String.class) {
			return string;
		} else if (clazz == Date.class) {
			return new SimpleDateFormat(options.dateFormat()).parse(string);
		}
//		else if (clazz == LocalDate.class) {
//			return LocalDate.parse(string, DateTimeFormatter.ofPattern(options.dateFormat()));
//		} else if (clazz == LocalDateTime.class) {
//			return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(options.dateFormat()));
//		}
		else if (clazz == byte.class || clazz == Byte.class) {
			return Byte.parseByte(string);
		} else if (clazz == short.class || clazz == Short.class) {
			return Short.parseShort(string);
		} else if (clazz == int.class || clazz == Integer.class) {
			return Integer.parseInt(string);
		} else if (clazz == long.class || clazz == Long.class) {
			return Long.parseLong(string);
		} else if (clazz == float.class || clazz == Float.class) {
			return Float.parseFloat(string);
		} else if (clazz == double.class || clazz == Double.class) {
			return Double.parseDouble(string);
		} else if (clazz == boolean.class || clazz == Boolean.class) {
			return Boolean.parseBoolean(string);
		}
//		else if (clazz == char.class || clazz == Character.class) {
//			return string.charAt(0);
//		}

		return string;
	}

	/**
	 * Convert an object to string by using field type
	 * Used for un-serialization
	 *
	 * @param object
	 * @param field
	 * @return converted value from object
	 */
	private String objectToString(Object object, Field field) {
		final Class<?> clazz = field.getType();

		if (clazz == String.class) {
			return object.toString();
		} else if (clazz == Date.class) {
			return new SimpleDateFormat(options.dateFormat()).format((Date) object);
		}
//		else if (clazz == LocalDate.class) {
//			return ((LocalDate) object).format(DateTimeFormatter.ofPattern(options.dateFormat()));
//		} else if (clazz == LocalDateTime.class) {
//			return ((LocalDateTime) object).format(DateTimeFormatter.ofPattern(options.dateFormat()));
//		}
		else {
			return String.valueOf(object);
		}
	}
}
