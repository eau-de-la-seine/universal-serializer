package fr.ekinci.universalserializer.format.binary;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Gokan EKINCI
 */
public abstract class AbstractBinarySerializer<T> implements Serializer<T, byte[]> {

	@Override
	public byte[] serialize(T objectToSerialize) throws SerializationException {
		// ByteArrayOutputStream close() method is empty
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		sendTo(objectToSerialize, baos);
		return baos.toByteArray();
	}

	@Override
	public T deserialize(byte[] objectToDeserialize) throws DeserializationException {
		// ByteArrayInputStream close() method is empty
		ByteArrayInputStream bais = new ByteArrayInputStream(objectToDeserialize);
		return receiveFrom(bais);
	}
}
