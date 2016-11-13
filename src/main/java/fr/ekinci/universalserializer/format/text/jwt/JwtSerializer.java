package fr.ekinci.universalserializer.format.text.jwt;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.google.gson.Gson;
import fr.ekinci.universalserializer.format.text.AbstractStringSerializer;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.format.text.jwt.exception.JwtSerializerException;
import fr.ekinci.universalserializer.format.text.jwt.exception.SignatureDeserializationException;

/**
 * JWT serializer
 *
 * Your implementation class may not implements {@link java.io.Serializable}
 * BUT be careful to NOT have circular references
 *
 * @author Gokan EKINCI
 */
public class JwtSerializer<T> extends AbstractStringSerializer<T> {
	// private final static String ENCODING = "UTF-8"; => use this for getBytes() ?
	private final Mac mac;
	private final String jsonHeader;
	private final Gson gson = new Gson();
	private final Type payloadType;

	public JwtSerializer(Type payloadType, String algorithm, String secret) throws JwtSerializerException {
		if (algorithm == null || payloadType == null || secret == null) {
			throw new IllegalArgumentException("Parameters in JwtSerializer's constructor must not be null");
		}

		try {
			// secret
			this.mac = Mac.getInstance(algorithm);
			this.mac.init(new SecretKeySpec(secret.getBytes(), algorithm));

			// payload
			this.payloadType = payloadType;

			// header
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("typ", "JWT");
			headerMap.put("alg", algorithm);
			this.jsonHeader = gson.toJson(headerMap);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			throw new JwtSerializerException(e);
		}
	}


	/**
	 * SIGN
	 * header.payload.secret
	 * => base64Url(header).base64Url(payload).base64Url(hmac(base64Url(header).base64Url(payload), secret))
	 */
	@Override
	public String serialize(T payloadObject) throws SerializationException {
		Objects.requireNonNull(payloadObject, "payloadObject must not be null");

		final Base64.Encoder encoder = Base64.getUrlEncoder();
		final String base64UrlEncodedHeader = encoder.encodeToString(jsonHeader.getBytes());
		final String base64UrlEncodedPayload = encoder.encodeToString(gson.toJson(payloadObject).getBytes());

		// Sign
		final byte[] signature = hmac((base64UrlEncodedHeader + "." + base64UrlEncodedPayload).getBytes());
		final String base64UrlEncodedSignature = encoder.encodeToString(signature);

		return base64UrlEncodedHeader + "." + base64UrlEncodedPayload + "." + base64UrlEncodedSignature;
	}

	/**
	 * VERIFY
	 */
	@Override
	public T deserialize(String objectToDeserialize) throws DeserializationException {
		Objects.requireNonNull(objectToDeserialize, "jsonWebToken must not be null");

		final String[] parts = objectToDeserialize.split("\\.");
		if (parts.length != 3) {
			throw new DeserializationException("jsonWebToken must respect `header.payload.secret` format");
		}

		// Verify
		final Base64.Decoder decoder = Base64.getUrlDecoder();
		final byte[] leftSideSignature = hmac((parts[0] + "." + parts[1]).getBytes()); // Reconstitution
		final byte[] rightSideSignature = decoder.decode(parts[2]);
		if (!MessageDigest.isEqual(leftSideSignature, rightSideSignature)) {
			throw new SignatureDeserializationException("Wrong signature during unserialization");
		}

		final String decodedPayload = new String(decoder.decode(parts[1]));
		return gson.fromJson(decodedPayload, payloadType);
	}

	@Override
	public void sendTo(T objectToSend, OutputStream outputStream) throws SerializationException {
		defaultSendTo(objectToSend, outputStream);
	}

	@Override
	public T receiveFrom(InputStream inputStream) throws DeserializationException {
		return defaultReceiveFrom(inputStream);
	}

	/**
	 * Note: doFinal method is not threadsafe, must be synchronized
	 *
	 * @param readableBytes
	 *
	 * @return hmac
	 */
	private synchronized byte[] hmac(byte[] readableBytes) {
		return mac.doFinal(readableBytes);
	}
}
