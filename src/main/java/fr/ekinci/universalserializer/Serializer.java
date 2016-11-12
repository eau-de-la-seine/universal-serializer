package fr.ekinci.universalserializer;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface for Java serialization
 *
 * @param <NATIVE_TYPE>
 * @param <SERIALIZED_TYPE>
 */
public interface Serializer<NATIVE_TYPE, SERIALIZED_TYPE> {

	/**
	 * Serialize your object
	 *
	 * @param objectToSerialize
	 * @return
	 * @throws SerializationException
	 */
	SERIALIZED_TYPE serialize(NATIVE_TYPE objectToSerialize) throws SerializationException;

	/**
	 * Deserialize your object
	 *
	 * @param objectToDeserialize
	 * @return
	 * @throws DeserializationException
	 */
	NATIVE_TYPE deserialize(SERIALIZED_TYPE objectToDeserialize) throws DeserializationException;

	/**
	 * Serialize and send your object to {@link OutputStream}
	 * The {@link OutputStream} is {@link OutputStream#flush()} inside this method, but {@link OutputStream#close()} outside
	 *
	 * @param objectToSend
	 * @param outputStream
	 * @throws SerializationException
	 */
	void sendTo(NATIVE_TYPE objectToSend, OutputStream outputStream) throws SerializationException;

	/**
	 * Receive {@link InputStream} and deserialize it to native typed object
	 * The {@link InputStream} is {@link InputStream#close()} outside
	 *
	 * @param inputStream
	 * @return
	 * @throws DeserializationException
	 */
	NATIVE_TYPE receiveFrom(InputStream inputStream) throws DeserializationException;
}
