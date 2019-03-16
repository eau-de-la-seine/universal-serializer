package fr.ekinci.universalserializer.format.binary;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Gives an implementation InputStream or OutputStream
 *
 * @author Gokan EKINCI
 */
public interface IStreamFactory<INPUT, OUTPUT> {
	INPUT inputStream(InputStream in) throws DeserializationException;
	OUTPUT outputStream(OutputStream out) throws SerializationException;
}
