package fr.ekinci.universalserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * A little strategy pattern applied for base64 serialization
 * 
 * @author Gokan EKINCI
 */
public abstract class AbstractBase64Serializer implements Serializer<String> {
    protected Base64.Encoder encoder;
    protected Base64.Decoder decoder; 
    
    public AbstractBase64Serializer(Base64.Encoder encoder, Base64.Decoder decoder){
        this.encoder = encoder;
        this.decoder = decoder;
    }
    
    @Override
    public <J> String serialize(J objectToSerialize) throws SerializationException {
        try(
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)
        ){
            oos.writeObject(objectToSerialize);
            return encoder.encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <J> J unserialize(String objectToUnserialize) throws UnserializationException {
        try(
            ByteArrayInputStream bais = new ByteArrayInputStream(decoder.decode(objectToUnserialize));
            ObjectInputStream ois = new ObjectInputStream(bais)
        ){
            return (J) ois.readObject(); 
        } catch (IOException | ClassNotFoundException e) {
            throw new UnserializationException(e);
        }
    }
}
