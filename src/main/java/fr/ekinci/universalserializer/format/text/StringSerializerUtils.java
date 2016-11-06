package fr.ekinci.universalserializer.format.text;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Util interface which contain a default method for String based transfer
 *
 * @author Gokan EKINCI
 */
public class StringSerializerUtils {
	public static void defaultTransferTo(
			Serializer<Object, String> serializer,
			Object objectToTransfer,
			OutputStream outputStream
	) throws SerializationException {
		try {
			outputStream.write(serializer.serialize(objectToTransfer).getBytes());
			outputStream.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
}