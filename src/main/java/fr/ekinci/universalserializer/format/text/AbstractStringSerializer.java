package fr.ekinci.universalserializer.format.text;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Gokan EKINCI
 */
public abstract class AbstractStringSerializer<T> implements Serializer<T, String> {

	@Override
	public void sendTo(T objectToTransfer, OutputStream outputStream) throws SerializationException {
		try {
			outputStream.write(serialize(objectToTransfer).getBytes());
			outputStream.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		// This implementation may change with Java 9's InputStream#readAllBytes()
		try {
			final byte buffer[] = new byte[1024];
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();

			for(int size; (size = inputStream.read(buffer)) != -1; ){
				baos.write(buffer, 0, size);
			}

			return deserialize(new String(baos.toByteArray()));
		} catch (IOException e) {
			throw new DeserializationException(e);
		}
	}
}
