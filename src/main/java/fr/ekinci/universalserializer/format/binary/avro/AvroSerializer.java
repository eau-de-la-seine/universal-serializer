package fr.ekinci.universalserializer.format.binary.avro;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.AbstractBinarySerializer;
import fr.ekinci.universalserializer.format.binary.IStreamFactory;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Avro serialization
 *
 * @author Gokan EKINCI
 */
public class AvroSerializer<T> extends AbstractBinarySerializer<T> {
	private final Schema schema;
	private final IStreamFactory<Decoder, Encoder> streamFactory;

	public AvroSerializer(Class<T> clazz, AvroOption option) {
		schema = ReflectData.AllowNull.get().getSchema(clazz);

		switch (option) {
			case BINARY:
				streamFactory = new BinaryAvroStreamFactory();
				break;
			default:
				streamFactory = new JsonAvroStreamFactory(schema);
		}
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		DatumWriter<T> writer = new ReflectDatumWriter<>(schema);
		Encoder encoder = streamFactory.outputStream(outputStream);

		try {
			writer.write(objectToSend, encoder);
			encoder.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		DatumReader<T> reader = new ReflectDatumReader<>(schema);
		Decoder decoder = streamFactory.inputStream(inputStream);
		try {
			return reader.read(null, decoder);
		} catch (IOException e) {
			throw new DeserializationException(e);
		}
	}

	private static class BinaryAvroStreamFactory implements IStreamFactory<Decoder, Encoder> {

		@Override
		public Decoder inputStream(InputStream in) {
			return DecoderFactory.get().binaryDecoder(in, null);
		}

		@Override
		public Encoder outputStream(OutputStream out) {
			return EncoderFactory.get().binaryEncoder(out, null);
		}
	}

	private static class JsonAvroStreamFactory implements IStreamFactory<Decoder, Encoder> {
		private final Schema schema;

		public JsonAvroStreamFactory(Schema schema) {
			this.schema = schema;
		}

		@Override
		public Decoder inputStream(InputStream in) throws DeserializationException {
			try {
				return DecoderFactory.get().jsonDecoder(schema, in);
			} catch (IOException e) {
				throw new DeserializationException(e);
			}
		}

		@Override
		public Encoder outputStream(OutputStream out) throws SerializationException {
			try {
				return EncoderFactory.get().jsonEncoder(schema, out);
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}
	}
}
