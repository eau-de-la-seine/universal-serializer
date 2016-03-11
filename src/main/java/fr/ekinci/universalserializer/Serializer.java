package fr.ekinci.universalserializer;

import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * An interface for Java serialization
 * 
 * @author Gokan EKINCI
 *
 * @param <D>
 */
public interface Serializer<D> {
    public <J> D serialize(J objectToSerialize) throws SerializationException;
    public <J> J unserialize(D objectToUnserialize) throws UnserializationException;
}
