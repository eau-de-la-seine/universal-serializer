package fr.ekinci.universalserializer.format.binary.protocolbuffers;

import com.google.protobuf.Message;
import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.exception.ProtobufSerializerException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Google Protocol Buffers Serialization
 *
 * @author Gokan EKINCI
 */
public class ProtobufSerializer<T extends Message> implements Serializer<T, byte[]> {
	private final Method serializeMethod;
	private final Method deserializeMethod;
	private final Method sendToMethod;
	private final Method receiveFromMethod;

	public ProtobufSerializer(Class<T> clazz) throws ProtobufSerializerException {
		try {
			serializeMethod = clazz.getMethod("toByteArray");
			deserializeMethod = clazz.getMethod("parseFrom", byte[].class);
			sendToMethod = clazz.getMethod("writeTo", OutputStream.class);
			receiveFromMethod = clazz.getMethod("parseFrom", InputStream.class);
		} catch (NoSuchMethodException e) {
			throw new ProtobufSerializerException(e);
		}
	}

	@Override
	public byte[] serialize(T objectToSerialize) throws SerializationException {
		try {
			return (byte[]) serializeMethod.invoke(objectToSerialize);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T deserialize(byte[] objectToDeserialize) throws DeserializationException {
		try {
			return (T) deserializeMethod.invoke(null, objectToDeserialize);
		} catch (Exception e) {
			throw new DeserializationException(e);
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		try {
			sendToMethod.invoke(objectToSend, outputStream);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		try {
			return (T) receiveFromMethod.invoke(null, inputStream);
		} catch (Exception e) {
			throw new DeserializationException(e);
		}
	}
}
