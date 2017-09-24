package fr.ekinci.universalserializer.format.text.jwt;

import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.text.jwt.exception.SignatureDeserializationException;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * JWT RSA serializer
 *
 * Your implementation class may not implements {@link java.io.Serializable}
 * BUT be careful to NOT have circular references
 *
 * @author Gokan EKINCI
 */
public class JwtRsaSerializer<T> extends AbstractJwtSerializer<T> {
	private final PrivateKey privateKey;
	private final PublicKey publicKey;

	public JwtRsaSerializer(Type payloadType, String algorithm, PrivateKey privateKey, PublicKey publicKey) {
		super(payloadType, algorithm);

		if (algorithm == null || payloadType == null || privateKey == null || publicKey == null) {
			throw new IllegalArgumentException("Parameters in JwtRsaSerializer's constructor must not be null");
		}

		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	@Override
	protected String sign(String base64UrlEncodedHeaderAndPayload) throws SerializationException {
		final Base64.Encoder encoder = Base64.getUrlEncoder();

		try {
			final byte[] signature = rsaSignature(base64UrlEncodedHeaderAndPayload.getBytes(StandardCharsets.UTF_8));
			final String base64UrlEncodedSignature = encoder.encodeToString(signature);
			return base64UrlEncodedHeaderAndPayload + "." + base64UrlEncodedSignature;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new SerializationException("An error happened during RSA signature");
		}
	}

	@Override
	protected void verify(String header, String payload, String signature) throws DeserializationException {
		final Base64.Decoder decoder = Base64.getUrlDecoder();

		try {
			final byte[] headerAndPayload = (header + "." + payload).getBytes(StandardCharsets.UTF_8);
			final byte[] signatureBytes = decoder.decode(signature);
			rsaVerification(headerAndPayload, signatureBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new SignatureDeserializationException("Wrong signature detected during deserialization");
		}
	}

	private byte[] rsaSignature(byte[] base64UrlEncodedHeaderAndPayload) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		final Signature signature = Signature.getInstance(algorithm);
		signature.initSign(this.privateKey);
		signature.update(base64UrlEncodedHeaderAndPayload);
		return signature.sign();
	}

	private void rsaVerification(byte[] sentHeaderAndPayload, byte[] sentSignature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		final Signature signature = Signature.getInstance(algorithm);
		signature.initVerify(this.publicKey);
		signature.update(sentHeaderAndPayload);
		signature.verify(sentSignature);
	}
}
