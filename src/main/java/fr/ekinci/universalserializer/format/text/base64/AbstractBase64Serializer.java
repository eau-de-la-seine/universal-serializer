package fr.ekinci.universalserializer.format.text.base64;

import java.io.*;
import java.util.Base64;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.format.text.AbstractStringSerializer;

/**
 * A little strategy pattern applied for base64 serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractBase64Serializer<T> extends AbstractStringSerializer<T> {
	protected final Base64.Encoder encoder;
	protected final Base64.Decoder decoder;
	protected final JavaSerializer<T> javaSerializer;

	public AbstractBase64Serializer(Base64.Encoder encoder, Base64.Decoder decoder) {
		this.javaSerializer = new JavaSerializer<>();
		this.encoder = encoder;
		this.decoder = decoder;
	}

	@Override
	public String serialize(T objectToSerialize) throws SerializationException {
		return encoder.encodeToString(javaSerializer.serialize(objectToSerialize));
	}

	@Override
	public T deserialize(String objectToDeserialize) throws DeserializationException {
		return javaSerializer.deserialize(decoder.decode(objectToDeserialize));
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		defaultSendTo(objectToSend, outputStream);
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		return defaultReceiveFrom(inputStream);
	}
}
