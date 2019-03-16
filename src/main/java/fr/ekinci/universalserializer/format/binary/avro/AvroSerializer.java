package fr.ekinci.universalserializer.format.binary.avro;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.AbstractBinarySerializer;
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

	public AvroSerializer(Class<T> clazz) {
		schema = ReflectData.AllowNull.get().getSchema(clazz);
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		DatumWriter<T> writer = new ReflectDatumWriter<>(schema);
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
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
		Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
		try {
			return reader.read(null, decoder);
		} catch (IOException e) {
			throw new DeserializationException(e);
		}
	}
}
