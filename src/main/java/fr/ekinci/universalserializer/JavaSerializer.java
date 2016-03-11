package fr.ekinci.universalserializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;


/**
 * A wrapper class for Java serialization
 * 
 * Note that J does not extends from Serializable, because 
 * you can use a type (like the List<T> interface) which 
 * is not Serializable at compile time but its 
 * implementation (like ArrayList<T> class) is serializable at runtime. 
 * 
 * @author Gokan EKINCI
 */
public class JavaSerializer implements Serializer<byte[]> {

    @Override
    public <J> byte[] serialize(J objectToSerialize) throws SerializationException {
        try(
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)
        ){
            oos.writeObject(objectToSerialize);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <J> J unserialize(byte[] objectToUnserialize) throws UnserializationException {
        try(
            ByteArrayInputStream bais = new ByteArrayInputStream(objectToUnserialize);
            ObjectInputStream ois = new ObjectInputStream(bais)
        ){
            return (J) ois.readObject(); 
        } catch (IOException | ClassNotFoundException e) {
            throw new UnserializationException(e);
        }
    }
}
