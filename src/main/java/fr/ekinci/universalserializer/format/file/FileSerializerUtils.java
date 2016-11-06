package fr.ekinci.universalserializer.format.file;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.List;

/**
 * Utils class for file serialization
 *
 * @author Gokan EKINCI
 */
public class FileSerializerUtils {

	public static <T> Path defaultSerialize(
			Serializer<List<T>, Path> fileSerializer,
			List<T> objectToSerialize,
			String destinationPath
	) throws SerializationException {
		try {
			final Path path = (destinationPath != null) ?
					Paths.get(destinationPath) :
					Files.createTempFile(null, null, new FileAttribute[0]);

			try (OutputStream outputStream = Files.newOutputStream(path)) {
				fileSerializer.transferTo(objectToSerialize, outputStream);
			}

			return path;
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
}

