package fr.ekinci.universalserializer.format.binary.java;

import java.io.*;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;


/**
 * Java serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * Note that USER_DEFINED_TYPE does not extends from Serializable, because
 * you can use a type (like the {@link java.util.List} interface) which
 * is not Serializable at compile time but its
 * implementation (like {@link java.util.ArrayList} class) is serializable at runtime.
 *
 * @author Gokan EKINCI
 */
public class JavaSerializer implements Serializer<Object, byte[]> {

	@Override
	public byte[] serialize(Object objectToSerialize) throws SerializationException {
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
	public <USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(byte[] objectToUnserialize) throws UnserializationException {
		try (
				ByteArrayInputStream bais = new ByteArrayInputStream(objectToUnserialize);
				ObjectInputStream ois = new ObjectInputStream(bais)
		) {
			return (USER_DEFINED_TYPE) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new UnserializationException(e);
		}
	}

	@Override
	public void transferTo(Object objectToTransfer, OutputStream outputStream) throws SerializationException {
		try {
			final ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(objectToTransfer);
			oos.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}
}
