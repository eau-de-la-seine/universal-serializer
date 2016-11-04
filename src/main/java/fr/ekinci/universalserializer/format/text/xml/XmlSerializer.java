package fr.ekinci.universalserializer.format.text.xml;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXB;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.UnserializationException;

/**
 * XML serializer (It use JAXB)
 *
 * Your implementation class may not implements {@link java.io.Serializable}
 * BUT be careful to NOT have circular references
 *
 * @author Gokan EKINCI
 */
public class XmlSerializer implements Serializer<Object, String> {
	// This attribute is used for unmarshaling
	private Class<?> objectType;

	public XmlSerializer(Class<?> objectType){
		this.objectType = objectType;
	}

	@Override
	public String serialize(Object objectToSerialize) throws SerializationException {
		try(StringWriter sw = new StringWriter()){
			JAXB.marshal(objectToSerialize, sw);
			return sw.toString();
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public <J> J unserialize(String objectToUnserialize) throws UnserializationException {
		try(StringReader reader = new StringReader(objectToUnserialize)){
			return (J) JAXB.unmarshal(reader, objectType);
		} catch (Exception e) {
			throw new UnserializationException(e);
		}
	}

	@Override
	public void transferTo(Object objectToTransfer, OutputStream outputStream) throws SerializationException {
		try {
			JAXB.marshal(objectToTransfer, outputStream);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
}
