package fr.ekinci.universalserializer.format.text.jwt.exception;


import fr.ekinci.universalserializer.exception.DeserializationException;

/**
 * @author Gokan EKINCI
 */
public class SignatureDeserializationException extends DeserializationException {

	public SignatureDeserializationException(String message) {
		super(message);
	}
}
