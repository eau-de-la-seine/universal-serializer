package fr.ekinci.universalserializer.format.text.base64;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.format.text.AbstractStringSerializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Base64 serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * @author Gokan EKINCI
 */
public class Base64Serializer<T> extends AbstractStringSerializer<T> {
	private final Base64.Encoder encoder;
	private final Base64.Decoder decoder;
	private final JavaSerializer<T> javaSerializer;

	public Base64Serializer(Base64Option option) {
		this.javaSerializer = new JavaSerializer<>();

		switch(option) {
			case MIME:
				this.encoder = Base64.getMimeEncoder();
				this.decoder = Base64.getMimeDecoder();
				break;
			case URL:
				this.encoder = Base64.getUrlEncoder();
				this.decoder = Base64.getUrlDecoder();
				break;
			default:
				this.encoder = Base64.getEncoder();
				this.decoder = Base64.getDecoder();
		}
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
