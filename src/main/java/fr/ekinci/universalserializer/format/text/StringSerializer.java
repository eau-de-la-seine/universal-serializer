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
public interface StringSerializer extends Serializer<Object, String> {
    @Override
    default void transferTo(Object objectToTransfer, OutputStream outputStream) throws SerializationException {
        try {
            outputStream.write(serialize(objectToTransfer).getBytes());
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}