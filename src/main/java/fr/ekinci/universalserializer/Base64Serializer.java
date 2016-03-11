package fr.ekinci.universalserializer;

import java.util.Base64;

/**
 * Base64 serialization
 * 
 * @author Gokan EKINCI
 */
public class Base64Serializer extends AbstractBase64Serializer {
    
    public Base64Serializer(){
        super(Base64.getEncoder(), Base64.getDecoder());
    }
}
