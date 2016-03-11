package fr.ekinci.universalserializer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * XML serialization (it use JAXB)
 * 
 * @author Gokan EKINCI
 */
public class XmlSerializer implements Serializer<String> {
    // This attribute is used for unmarshaling
    private Class<?> objectType;

    public XmlSerializer(Class<?> objectType){
        this.objectType = objectType;
    }
    
    @Override
    public <J> String serialize(J objectToSerialize) throws SerializationException {
        try(StringWriter sw = new StringWriter()){
            JAXB.marshal(objectToSerialize, sw);
            return sw.toString();
        } catch (IOException | DataBindingException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <J> J unserialize(String objectToUnserialize) throws UnserializationException {
        try(StringReader reader = new StringReader(objectToUnserialize)){
            return (J) JAXB.unmarshal(reader, objectType);
        }
    }
}
