package fr.ekinci.universalserializer.format.text.jwt;

import com.google.gson.Gson;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.AbstractStringSerializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gokan EKINCI
 */
public abstract class AbstractJwtSerializer<T> extends AbstractStringSerializer<T> {
	protected final String jsonHeader;
	protected final Type payloadType;
	protected final String algorithm;
	protected final Gson gson = new Gson();

	public AbstractJwtSerializer(Type payloadType, String algorithm) {
		this.algorithm = algorithm;
		this.payloadType = payloadType;

		// header
		final Map<String, String> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", algorithm);
		this.jsonHeader = gson.toJson(headerMap);
	}

	/**
	 * A.k.a. SIGN
	 */
	@Override
	public String serialize(T payloadObject) throws SerializationException {
		Objects.requireNonNull(payloadObject, "payloadObject must not be null");

		final Base64.Encoder encoder = Base64.getUrlEncoder();
		final String base64UrlEncodedHeader = encoder.encodeToString(jsonHeader.getBytes(StandardCharsets.UTF_8));
		final String base64UrlEncodedPayload = encoder.encodeToString(gson.toJson(payloadObject).getBytes(StandardCharsets.UTF_8));

		// Sign
		return sign(base64UrlEncodedHeader + "." + base64UrlEncodedPayload);
	}

	/**
	 * A.k.a. VERIFY
	 */
	@Override
	public T deserialize(String jsonWebToken) throws DeserializationException {
		Objects.requireNonNull(jsonWebToken, "jsonWebToken must not be null");

		final String[] parts = jsonWebToken.split("\\.");
		if (parts.length != 3) {
			throw new DeserializationException("jsonWebToken must respect `header.payload.secret` format");
		}

		// Verify (throws exception if invalid)
		verify(parts[0], parts[1], parts[2]);

		final Base64.Decoder decoder = Base64.getUrlDecoder();
		final String decodedPayload = new String(decoder.decode(parts[1]), StandardCharsets.UTF_8);
		return gson.fromJson(decodedPayload, payloadType);
	}

	/**
	 * SIGN given base64UrlEncodedHeaderAndPayload
	 *
	 * @param base64UrlEncodedHeaderAndPayload 	It's base64UrlEncodedHeader.base64UrlEncodedPayload
	 * @return 									base64UrlEncodedHeaderAndPayload.base64UrlEncodedSignature
	 * @throws SerializationException
	 */
	protected abstract String sign(String base64UrlEncodedHeaderAndPayload)
		throws SerializationException;

	/**
	 * VERIFY given header.payload / signature
	 *
	 * @param header		JWT Header
	 * @param payload		JWT Payload
	 * @param signature		JWT Signature
	 * @throws DeserializationException
	 */
	protected abstract void verify(String header, String payload, String signature)
		throws DeserializationException;
}
