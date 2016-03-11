package fr.ekinci.universalserializer.exception;


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
