package fr.ekinci.universalserializer;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
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
	 * Unserialize your object
	 *
	 * @param objectToUnserialize
	 * @param <USER_DEFINED_TYPE>
	 * @return
	 * @throws UnserializationException
	 */
	<USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(SERIALIZED_TYPE objectToUnserialize) throws UnserializationException;

	/**
	 * Serialize and transfer your object to {@link OutputStream}
	 *
	 * @param objectToTransfer
	 * @param outputStream
	 * @throws SerializationException
	 */
	void transferTo(NATIVE_TYPE objectToTransfer, OutputStream outputStream) throws SerializationException;
}
