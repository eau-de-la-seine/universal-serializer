package fr.ekinci.universalserializer.exception;

/**
 * @author Gokan EKINCI
 */
public class SerializationException extends Exception {
    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Throwable t) {
        super(t);
    }
}
