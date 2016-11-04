package fr.ekinci.universalserializer.format.file.csv;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.AbstractFileSerializer;
import fr.ekinci.universalserializer.format.file.FileOptions;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * CSV serialization
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
						LocalDate.class,
						LocalDateTime.class,
						boolean.class,
						Boolean.class,
						char.class,
						Character.class
				},
				clazz,
				options
		);
	}

	@Override
	public Path serialize(List<T> objectToSerialize) throws SerializationException {
		return null;
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

	@Override
	public void transferTo(List<T> objectToTransfer, OutputStream outputStream) throws SerializationException {

	}

	private void setObjectFieldValue(T tuple, int fieldIndex, String stringValue) throws ParseException, IllegalAccessException {
		final Field field = requiredFields.get(fieldIndex);
		final Object fieldValue = stringToObject(stringValue, field);
		field.set(tuple, fieldValue);
	}

	/**
	 * Convert a string to object by using index of {@link AbstractFileSerializer#requiredFields}
	 *
	 * @param string     String to transform
	 * @param field      Field used to retrieve type
	 * @return
	 */
	private Object stringToObject(String string, Field field) throws ParseException {
		final Class<?> clazz = field.getType();

		if (clazz == String.class) {
			return string;
		} else if (clazz == Date.class) {
			return new SimpleDateFormat(options.dateFormat()).parse(string);
		} else if (clazz == LocalDate.class) {
			return LocalDate.parse(string, DateTimeFormatter.ofPattern(options.dateFormat()));
		} else if (clazz == LocalDateTime.class) {
			return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(options.dateFormat()));
		} else if (clazz == byte.class || clazz == Byte.class) {
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
		} else if (clazz == char.class || clazz == Character.class) {
			return string.charAt(0);
		}

		return string;
	}
}
