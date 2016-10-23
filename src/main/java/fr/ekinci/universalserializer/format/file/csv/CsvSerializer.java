package fr.ekinci.universalserializer.format.file.csv;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;
import fr.ekinci.universalserializer.format.file.AbstractFileSerializer;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV serialization
 *
 * @author Gokan EKINCI
 */
public class CsvSerializer<T> extends AbstractFileSerializer<T> {

    public CsvSerializer() {
        // TODO
        super(null, null, null);
    }

    @Override
    public Path serialize(List<T> objectToSerialize) throws SerializationException {
        return null;
    }

    @Override
    public <USER_DEFINED_TYPE> USER_DEFINED_TYPE unserialize(Path objectToUnserialize) throws UnserializationException {
        return null;
    }

    @Override
    public void transferTo(List<T> objectToTransfer, OutputStream outputStream) throws SerializationException {

    }
}
