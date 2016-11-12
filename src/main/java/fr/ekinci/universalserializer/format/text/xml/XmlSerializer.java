package fr.ekinci.universalserializer.format.text.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXB;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;

/**
 * XML serializer (It use JAXB)
 *
 * Your implementation class may not implements {@link java.io.Serializable}
 * BUT be careful to NOT have circular references
 *
 * @author Gokan EKINCI
 */
public class XmlSerializer<T> implements Serializer<T, String> {
	// This attribute is used for unmarshaling
	private Class<T> objectType;

	public XmlSerializer(Class<T> objectType){
		this.objectType = objectType;
	}

	@Override
	public String serialize(T objectToSerialize) throws SerializationException {
		try(StringWriter sw = new StringWriter()){
			JAXB.marshal(objectToSerialize, sw);
			return sw.toString();
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T deserialize(String objectToDeserialize) throws DeserializationException {
		try(StringReader reader = new StringReader(objectToDeserialize)){
			return JAXB.unmarshal(reader, objectType);
		} catch (Exception e) {
			throw new DeserializationException(e);
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		try {
			JAXB.marshal(objectToSend, outputStream);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		try {
			return JAXB.unmarshal(inputStream, objectType);
		} catch (Exception e) {
			throw new DeserializationException(e);
		}
	}
}
