package fr.ekinci.universalserializer.exception;

/**
 * @author Gokan EKINCI
 */
public class DeserializationException extends Exception {
	public DeserializationException(String message) {
		super(message);
	}

	public DeserializationException(Throwable t) {
		super(t);
	}
}
