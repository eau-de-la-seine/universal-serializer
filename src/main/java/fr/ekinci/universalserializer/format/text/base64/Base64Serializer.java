package fr.ekinci.universalserializer.format.text.base64;

import java.util.Base64;

/**
 * Base64 serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * @author Gokan EKINCI
 */
public class Base64Serializer<T> extends AbstractBase64Serializer<T> {

	public Base64Serializer() {
		super(Base64.getEncoder(), Base64.getDecoder());
	}
}
