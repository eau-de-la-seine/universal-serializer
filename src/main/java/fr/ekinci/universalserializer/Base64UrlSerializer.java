package fr.ekinci.universalserializer;

import java.util.Base64;

/**
 * Base64Url serialization
 * 
 * @author Gokan EKINCI
 */
public class Base64UrlSerializer extends AbstractBase64Serializer {
    
    public Base64UrlSerializer() {
        super(Base64.getUrlEncoder(), Base64.getUrlDecoder());
    }
}
