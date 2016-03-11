package fr.ekinci.universalserializer.test;

import org.junit.Assert;
import org.junit.Test;
import fr.ekinci.universalserializer.JavaSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;


/**
 *
 * A simple test of serialization and unserialization
 * 
 * @author Gokan EKINCI
 */
public class JavaSerializerTest {

    @Test
    public void testSerializeAndUnSerialze(){
        JavaSerializer s = new JavaSerializer();
        DumbClass originalObject = new DumbClass();
        originalObject.setAttr(99);
        
        try {
            // Serialization
            byte[] ser = s.serialize(originalObject);
            
            System.out.println("Java object size: " + ser.length);
            
            // Unserialization
            DumbClass unserializedObject = s.unserialize(ser);
            
            Assert.assertEquals(originalObject.getAttr(), unserializedObject.getAttr());
        } catch (SerializationException | UnserializationException e) {
            e.printStackTrace();
        }
    }

}
