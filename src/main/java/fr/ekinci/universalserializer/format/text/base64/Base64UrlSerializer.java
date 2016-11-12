package fr.ekinci.universalserializer.format.text.base64;

import java.util.Base64;

/**
 * Base64Url serialization
 *
 * Your implementation class must implements {@link java.io.Serializable}
 *
 * @author Gokan EKINCI
 */
public class Base64UrlSerializer<T> extends AbstractBase64Serializer<T> {

	public Base64UrlSerializer() {
		super(Base64.getUrlEncoder(), Base64.getUrlDecoder());
	}
}
