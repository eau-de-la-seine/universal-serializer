package fr.ekinci.universalserializer.format.file;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
public abstract class AbstractFileSerializer<T> implements Serializer<List<T>, Path> {
	protected final Class<T> clazz;
	protected final FileInfo fileInfo;
	protected final FileOptions options;
	protected final int nbColumns;
	protected final List<Field> requiredFields;

	public AbstractFileSerializer(Class<?>[] authorizedFieldTypes, Class<T> clazz, FileOptions options) {
		this.clazz = clazz;
		this.options = options;

		// Class must be annotated with FileInfo
		this.fileInfo = checkFileInfoAnnotation();

		// FileInfo must have same number of class fields and header names
		this.nbColumns = checkSizes();

		// Class must have valid attributes (field types)
		this.requiredFields = checkRequiredFields(
				getAllDeclaredFields(new ArrayList<>(), clazz),
				authorizedFieldTypes
		);

		// Make requiredFields accessible true once for performance issue
		for (Field requiredField : requiredFields) {
			requiredField.setAccessible(true);
		}
	}

	/**
	 * Check if {@link FileInfo} is present
	 *
	 * @return
	 */
	protected FileInfo checkFileInfoAnnotation() {
		if (!clazz.isAnnotationPresent(FileInfo.class)) {
			throw new IllegalArgumentException("Class must be annotated with @FileInfo");
		}

		return clazz.getAnnotation(FileInfo.class);
	}

	/**
	 * Check if {@link FileInfo#orderedFieldNames()} has at least 1 element
	 * <p>
	 * Check if {@link FileInfo#orderedFieldNames()} and {@link FileInfo#headerColumnNames()}
	 * has the same length then return the length
	 *
	 * @return
	 */
	protected int checkSizes() {
		final int nbColumns = fileInfo.orderedFieldNames().length;

		if (nbColumns < 1) {
			throw new IllegalArgumentException("FileInfo#orderedFieldNames must have at least 1 element");
		}

		if (options.hasHeader() && nbColumns != fileInfo.headerColumnNames().length) {
			throw new IllegalArgumentException("Header is ON, but FileInfo#orderedFieldNames and FileInfo#headerColumnNames do not have the same number of elements");
		}

		return nbColumns;
	}

	/**
	 * Check if field type is in authorizedFieldTypes and return field
	 *
	 * @param field
	 * @param authorizedFieldTypes
	 * @return
	 */
	protected Field checkField(Field field, Class<?>[] authorizedFieldTypes) {
		boolean fieldTypeFound = false;
		for (Class<?> clazz : authorizedFieldTypes) {
			if (field.getType() == clazz) {
				fieldTypeFound = true;
				break;
			}
		}

		if (!fieldTypeFound) {
			throw new IllegalArgumentException(
					"The field named `" + field.getName()
							+ "` which has `" + field.getType()
							+ "` type is NOT an accepted type"
			);
		}

		return field;
	}

	/**
	 * Check and return required fields
	 *
	 * @param allFields
	 * @param authorizedFieldTypes
	 * @return
	 */
	protected List<Field> checkRequiredFields(List<Field> allFields, final Class<?>[] authorizedFieldTypes) {
		final List<Field> result = new ArrayList<>();
		for (String fieldName : fileInfo.orderedFieldNames()) {
			boolean fieldNameFound = false;
			for (Field field : allFields) {
				if (field.getName().equals(fieldName)) {
					result.add(checkField(field, authorizedFieldTypes));
					fieldNameFound = true;
					break;
				}
			}
			if (!fieldNameFound) {
				throw new IllegalArgumentException("The field named `" + fieldName + "` has not been found in " + clazz.getName());
			}
		}
		return result;
	}

	/**
	 * Get all declared fields from Class<?>
	 * Note : getDeclaredFields() may return private attributes, but does not return Parents fields
	 *
	 * @param fields
	 * @param type
	 * @return
	 */
	protected static List<Field> getAllDeclaredFields(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields = getAllDeclaredFields(fields, type.getSuperclass());
		}

		return fields;
	}


	public Path defaultSerialize(
			List<T> objectToSerialize,
			String destinationPath
	) throws SerializationException {
		try {
			final Path path = (destinationPath != null) ?
					Paths.get(destinationPath) :
					Files.createTempFile(null, null, new FileAttribute[0]);

			try (OutputStream outputStream = Files.newOutputStream(path)) {
				sendTo(objectToSerialize, outputStream);
			}

			return path;
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	public List<T> defaultDeserialize(
			String destinationPath
	) throws DeserializationException {
		try {
			final Path path = (destinationPath != null) ?
					Paths.get(destinationPath) :
					Files.createTempFile(null, null, new FileAttribute[0]);

			try (InputStream inputStream = Files.newInputStream(path)) {
				return receiveFrom(inputStream);
			}

		} catch (IOException e) {
			throw new DeserializationException(e);
		}
	}
}
