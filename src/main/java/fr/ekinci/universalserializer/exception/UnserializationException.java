package fr.ekinci.universalserializer.exception;

/**
 * @author Gokan EKINCI
 */
public class UnserializationException extends Exception {
    public UnserializationException(String message) {
        super(message);
    }

    public UnserializationException(Throwable t) {
        super(t);
    }
}
