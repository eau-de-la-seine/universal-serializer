package fr.ekinci.universalserializer;

import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.google.gson.Gson;
import fr.ekinci.universalserializer.exception.JWTSerializerException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.SignatureUnserializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * 
 * @author Gokan EKINCI
 */
public class JWTSerializer implements Serializer<String>{
    // private final static String ENCODING = "UTF-8"; => use this for getBytes() ?
    private Mac mac;
    private String jsonHeader; 
    private Gson gson = new Gson();
    private Type payloadType;
    
    public JWTSerializer(String algorithm, Type payloadType, String secret) throws JWTSerializerException {
        if(algorithm == null || payloadType == null || secret == null){
            throw new IllegalArgumentException("Parameters in JWTSerializer's constructor must not be null");
        }
        
        try {
            // secret
            this.mac = Mac.getInstance(algorithm);
            this.mac.init(new SecretKeySpec(secret.getBytes(), algorithm));
  
            // payload
            this.payloadType = payloadType;
        
            // header 
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", algorithm);
            this.jsonHeader = gson.toJson(headerMap);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new JWTSerializerException(e);
        }
    }

    
    /**
     * SIGN
     * header.payload.secret
     *     => base64Url(header).base64Url(payload).base64Url(hmac(base64Url(header).base64Url(payload), secret))
     */
    @Override
    public <J> String serialize(J payloadObject) throws SerializationException {
        if(payloadObject == null){
            throw new IllegalArgumentException("payloadObject must not be null");
        }
        
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String base64UrlEncodedHeader = encoder.encodeToString(jsonHeader.getBytes());
        String base64UrlEncodedPayload = encoder.encodeToString(gson.toJson(payloadObject).getBytes());
        
        // Sign
        byte[] signature = this.mac.doFinal((base64UrlEncodedHeader + "." + base64UrlEncodedPayload).getBytes());
        String base64UrlEncodedSignature = encoder.encodeToString(signature);
        
        return base64UrlEncodedHeader + "." + base64UrlEncodedPayload + "." + base64UrlEncodedSignature;
    }

    /**
     * VERIFY
     */
    @Override
    public <J> J unserialize(String jsonWebToken) throws UnserializationException {
        if(jsonWebToken == null){
            throw new IllegalArgumentException("jsonWebToken must not be null");
        }

        String[] parts = jsonWebToken.split("\\.");
        System.out.println("parts.length: " + parts.length);
        if(parts.length != 3){
            throw new UnserializationException("jsonWebToken must respect `header.payload.secret` format");
        }
        
        // Verify
        Base64.Decoder decoder = Base64.getUrlDecoder();        
        byte[] leftSideSignature = this.mac.doFinal((parts[0] + "." + parts[1]).getBytes()); // Reconstitution
        byte[] rightSideSignature = decoder.decode(parts[2]);
        if(!MessageDigest.isEqual(leftSideSignature, rightSideSignature)){
            throw new SignatureUnserializationException("Wrong signature during unserialization");
        }

        String decodedPayload = new String(decoder.decode(parts[1]));
        return gson.fromJson(decodedPayload, payloadType);
    }

}
