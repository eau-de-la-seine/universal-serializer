package fr.ekinci.universalserializer.format.text.jwt.exception;


import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * 
 * @author Gokan EKINCI
 */
public class SignatureUnserializationException extends UnserializationException {

    public SignatureUnserializationException(String message) {
        super(message);
    }
    
    public SignatureUnserializationException(Throwable t) {
        super(t);
    }
}
