package fr.ekinci.universalserializer.format.binary.java;

import java.io.*;
import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;


/**
 * Java serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * Note that J does not extends from Serializable, because 
 * you can use a type (like the List<T> interface) which 
 * is not Serializable at compile time but its 
 * implementation (like ArrayList<T> class) is serializable at runtime. 
 * 
 * @author Gokan EKINCI
 */
public class JavaSerializer implements Serializer<Object, byte[]> {

    @Override
    public byte[] serialize(Object objectToSerialize) throws SerializationException {
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

    @Override
    public void transferTo(Object objectToTransfer, OutputStream outputStream) throws SerializationException {
        try(
            ObjectOutputStream oos = new ObjectOutputStream(outputStream)
        ){
            oos.writeObject(objectToTransfer);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
