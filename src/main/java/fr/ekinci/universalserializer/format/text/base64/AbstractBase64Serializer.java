package fr.ekinci.universalserializer.format.text.base64;

import java.io.*;
import java.util.Base64;

import fr.ekinci.universalserializer.format.text.StringSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * A little strategy pattern applied for base64 serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractBase64Serializer implements StringSerializer {
    protected Base64.Encoder encoder;
    protected Base64.Decoder decoder;

    public AbstractBase64Serializer(Base64.Encoder encoder, Base64.Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String serialize(Object objectToSerialize) throws SerializationException {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos)
        ) {
            oos.writeObject(objectToSerialize);
            return encoder.encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <J> J unserialize(String objectToUnserialize) throws UnserializationException {
        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(decoder.decode(objectToUnserialize));
                ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return (J) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new UnserializationException(e);
        }
    }
}
