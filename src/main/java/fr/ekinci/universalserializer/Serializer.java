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
    SERIALIZED_TYPE serialize(NATIVE_TYPE objectToSerialize) throws SerializationException;
    <USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(SERIALIZED_TYPE objectToUnserialize) throws UnserializationException;
    void transferTo(NATIVE_TYPE objectToTransfer, OutputStream outputStream) throws SerializationException;
}
