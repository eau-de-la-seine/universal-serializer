package fr.ekinci.universalserializer.test;

import org.junit.Assert;
import org.junit.Test;
import fr.ekinci.universalserializer.Algorithm;
import fr.ekinci.universalserializer.JWTSerializer;
import fr.ekinci.universalserializer.exception.JWTSerializerException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

public class JWTSerializerTest {
    public final static String SECRET = "546T78UINqqsvfzfs<vs<sdvç_è-('éU87Y89YG87";
    
    @Test
    public void testSerializeAndUnSerialze(){
        try {
            DumbClass dc = new DumbClass();
            dc.setAttr(123456789);
            
            JWTSerializer s = new JWTSerializer(Algorithm.HS256, DumbClass.class, SECRET);
            
            // SIGN
            String sendToClient = s.serialize(dc);
            
            // VERIFY
            DumbClass newDc = s.unserialize(sendToClient);
            Assert.assertEquals(newDc.getAttr(), 123456789);
            
            // s.serialize(payloadObject)
        } catch (JWTSerializerException | SerializationException | UnserializationException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

}
