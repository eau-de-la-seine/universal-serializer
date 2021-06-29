package fr.ekinci.universalserializer.format.binary.thrift;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.AbstractBinarySerializer;
import fr.ekinci.universalserializer.format.binary.IStreamFactory;
import fr.ekinci.universalserializer.format.binary.thrift.exception.ThriftSerializerException;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransportException;

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
public class ThriftSerializer<T extends TBase> extends AbstractBinarySerializer<T> {
	private final Constructor<T> constructor;
	private final IStreamFactory<TProtocol, TProtocol> streamFactory;

	public ThriftSerializer(Class<T> clazz, ThriftOption option) throws ThriftSerializerException {
		try {
			this.constructor = clazz.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new ThriftSerializerException(e);
		}

		switch (option) {
			case COMPACT:
				streamFactory = new CompactThriftStreamFactory();
				break;
			default:
				streamFactory = new BinaryThriftStreamFactory();
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		try {
			objectToSend.write(streamFactory.outputStream(outputStream));
			outputStream.flush();
		} catch (IOException | TException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		try {
			T receivedObject = constructor.newInstance();
			receivedObject.read(streamFactory.inputStream(inputStream));
			return receivedObject;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | TException e) {
			throw new DeserializationException(e);
		}
	}

	private static class BinaryThriftStreamFactory implements IStreamFactory<TProtocol, TProtocol> {

		@Override
		public TProtocol inputStream(InputStream in) throws DeserializationException {
			try {
				return new TBinaryProtocol(new TIOStreamTransport(in));
			} catch (TTransportException e) {
				throw new DeserializationException(e);
			}
		}

		@Override
		public TProtocol outputStream(OutputStream out) throws SerializationException {
			try {
				return new TBinaryProtocol(new TIOStreamTransport(out));
			} catch (TTransportException e) {
				throw new SerializationException(e);
			}
		}
	}

	private static class CompactThriftStreamFactory implements IStreamFactory<TProtocol, TProtocol> {

		@Override
		public TProtocol inputStream(InputStream in) throws DeserializationException {
			try {
				return new TCompactProtocol(new TIOStreamTransport(in));
			} catch (TTransportException e) {
				throw new DeserializationException(e);
			}
		}

		@Override
		public TProtocol outputStream(OutputStream out) throws SerializationException {
			try {
				return new TCompactProtocol(new TIOStreamTransport(out));
			} catch (TTransportException e) {
				throw new SerializationException(e);
			}
		}
	}
}
