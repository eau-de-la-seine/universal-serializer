package fr.ekinci.universalserializer.format.text.jwt;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.jwt.exception.SignatureDeserializationException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * JWT HMAC serializer
 *
 * Your implementation class may not implements {@link java.io.Serializable}
 * BUT be careful to NOT have circular references
 *
 * JSON Web Token format: header.payload.secret
 * => base64Url(header).base64Url(payload).base64Url(hmac(base64Url(header).base64Url(payload), secret))
 *
 * @author Gokan EKINCI
 */
public class JwtHmacSerializer<T> extends AbstractJwtSerializer<T> {
	private final byte[] secretKey;

	public JwtHmacSerializer(Type payloadType, String algorithm, byte[] secretKey) {
		super(payloadType, algorithm);

		if (algorithm == null || payloadType == null || secretKey == null) {
			throw new IllegalArgumentException("Parameters in JwtHmacSerializer's constructor must not be null");
		}

		this.secretKey = secretKey;
	}

	@Override
	protected String sign(String base64UrlEncodedHeaderAndPayload) throws SerializationException {
		final Base64.Encoder encoder = Base64.getUrlEncoder();

		try {
			final byte[] signature = hmac(base64UrlEncodedHeaderAndPayload.getBytes(StandardCharsets.UTF_8));
			final String base64UrlEncodedSignature = encoder.encodeToString(signature);
			return base64UrlEncodedHeaderAndPayload + "." + base64UrlEncodedSignature;
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new SerializationException("An error happened during HMAC generation");
		}
	}

	@Override
	protected void verify(String header, String payload, String signature) throws DeserializationException {
		final Base64.Decoder decoder = Base64.getUrlDecoder();

		try {
			final byte[] leftSideHeaderAndPayload = hmac((header + "." + payload).getBytes(StandardCharsets.UTF_8)); // Reconstitution
			final byte[] rightSideSignature = decoder.decode(signature);
			if (!MessageDigest.isEqual(leftSideHeaderAndPayload, rightSideSignature)) {
				throw new SignatureDeserializationException("Wrong signature detected during deserialization");
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new DeserializationException("An error happened during HMAC generation");
		}
	}

	/**
	 * HMAC implementation: Generate hash with given content
	 *
	 * Careful : {@link Mac#doFinal(byte[])} is not threadsafe ;)
	 *
	 * @param readableBytes					Content
	 * @return 								hmac
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private byte[] hmac(byte[] readableBytes) throws NoSuchAlgorithmException, InvalidKeyException {
		final Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(secretKey, algorithm));
		return mac.doFinal(readableBytes);
	}
}
