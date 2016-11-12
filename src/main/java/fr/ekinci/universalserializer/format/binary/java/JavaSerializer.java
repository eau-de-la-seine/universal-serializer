package fr.ekinci.universalserializer.format.binary.java;

import java.io.*;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;


/**
 * Java serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * Note that T does not extends from Serializable, because
 * you can use a type (like the {@link java.util.List} interface) which
 * is not Serializable at compile time but its
 * implementation (like {@link java.util.ArrayList} class) is serializable at runtime.
 *
 * @author Gokan EKINCI
 */
public class JavaSerializer<T> implements Serializer<T, byte[]> {

	@Override
	public byte[] serialize(T objectToSerialize) throws SerializationException {
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos)
		) {
			oos.writeObject(objectToSerialize);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T deserialize(byte[] objectToDeserialize) throws DeserializationException {
		try (
				ByteArrayInputStream bais = new ByteArrayInputStream(objectToDeserialize);
				ObjectInputStream ois = new ObjectInputStream(bais)
		) {
			return (T) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserializationException(e);
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		try {
			final ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(objectToSend);
			oos.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		try {
			final ObjectInputStream ois = new ObjectInputStream(inputStream);
			return (T) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserializationException(e);
		}
	}
}
