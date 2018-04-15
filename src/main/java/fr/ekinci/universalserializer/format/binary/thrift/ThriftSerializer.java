package fr.ekinci.universalserializer.format.binary.thrift;

import fr.ekinci.universalserializer.Serializer;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.thrift.exception.ThriftSerializerException;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Thrift serialization
 *
 * @author Gokan EKINCI
 */
public class ThriftSerializer<T extends TBase> implements Serializer<T, byte[]> {
	private final Constructor<T> constructor;
	private final StreamHelper streamHelper;
	private final TSerializer serializer;
	private final TDeserializer deserializer;

	public ThriftSerializer(Class<T> clazz, ThriftOption option) throws ThriftSerializerException {
		try {
			this.constructor = clazz.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new ThriftSerializerException(e);
		}

		switch (option) {
			case COMPACT:
				serializer = new TSerializer(new TCompactProtocol.Factory());
				deserializer = new TDeserializer(new TCompactProtocol.Factory());
				streamHelper = new CompactStreamHelper();
				break;
			default:
				serializer = new TSerializer(new TBinaryProtocol.Factory());
				deserializer = new TDeserializer(new TBinaryProtocol.Factory());
				streamHelper = new BinaryStreamHelper();
		}
	}

	@Override
	public byte[] serialize(T objectToSerialize) throws SerializationException {
		try {
			return serializer.serialize(objectToSerialize);
		} catch (TException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T deserialize(byte[] objectToDeserialize) throws DeserializationException {
		try {
			T deserializedObject = constructor.newInstance();
			deserializer.deserialize(deserializedObject, objectToDeserialize);
			return deserializedObject;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | TException e) {
			throw new DeserializationException(e);
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		try {
			objectToSend.write(streamHelper.outputStream(outputStream));
			outputStream.flush();
		} catch (IOException | TException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		try {
			T receivedObject = constructor.newInstance();
			receivedObject.read(streamHelper.inputStream(inputStream));
			return receivedObject;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | TException e) {
			throw new DeserializationException(e);
		}
	}

	private interface StreamHelper {
		TProtocol outputStream(OutputStream out);
		TProtocol inputStream(InputStream in);
	}

	private static class BinaryStreamHelper implements StreamHelper {

		@Override
		public TProtocol outputStream(OutputStream out) {
			return new TBinaryProtocol(new TIOStreamTransport(out));
		}

		@Override
		public TProtocol inputStream(InputStream in) {
			return new TBinaryProtocol(new TIOStreamTransport(in));
		}
	}

	private static class CompactStreamHelper implements StreamHelper {

		@Override
		public TProtocol outputStream(OutputStream out) {
			return new TCompactProtocol(new TIOStreamTransport(out));
		}

		@Override
		public TProtocol inputStream(InputStream in) {
			return new TCompactProtocol(new TIOStreamTransport(in));
		}
	}
}