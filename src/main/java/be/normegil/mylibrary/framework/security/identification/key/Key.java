package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.exception.InvalidKeySpecRuntimeException;
import be.normegil.mylibrary.framework.exception.NoSuchAlgorithmRuntimeException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@javax.persistence.Entity
@Access(AccessType.FIELD)
public class Key extends Entity {

	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "keyType")
	private KeyType type;

	@NotNull
	private byte[] encodedPublicKey;
	@NotNull
	private byte[] encodedPrivateKey;

	/* Should not be used outside of Hibernate context */
	protected Key() {
	}

	public Key(@NotEmpty final String name, @NotNull final KeyType type) {
		super();
		this.name = name;
		this.type = type;

		KeyPair keyPair = getKeyPair(type.getAlgorythmName(), type.getDefaultKeySize());

		encodedPublicKey = encodePublicKey(keyPair.getPublic().getEncoded());
		encodedPrivateKey = encodePrivateKey(keyPair.getPrivate().getEncoded());
	}

	public String getName() {
		return name;
	}

	public KeyType getType() {
		return type;
	}

	public PublicKey getPublicKey() {
		try {
			return getKeyFactory(getType().getAlgorythmName())
					.generatePublic(decodePublicKey(encodedPublicKey));
		} catch (InvalidKeySpecException e) {
			throw new InvalidKeySpecRuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		try {
			return getKeyFactory(getType().getAlgorythmName())
					.generatePrivate(decodePrivateKey(encodedPrivateKey));
		} catch (InvalidKeySpecException e) {
			throw new InvalidKeySpecRuntimeException(e);
		}
	}

	protected KeyPair getKeyPair(final String algorythmName, final int keySize) {
		KeyPairGenerator generator = getKeyPairGenerator(algorythmName);
		generator.initialize(keySize, new SecureRandom());
		return generator.generateKeyPair();
	}

	protected byte[] encodePublicKey(@NotNull final byte[] publicKey) {
		return new X509EncodedKeySpec(publicKey).getEncoded();
	}

	protected EncodedKeySpec decodePublicKey(@NotNull final byte[] encodedPublicKey) {
		return new X509EncodedKeySpec(encodedPublicKey);
	}

	protected byte[] encodePrivateKey(@NotNull final byte[] privateKey) {
		return new PKCS8EncodedKeySpec(privateKey).getEncoded();
	}

	protected EncodedKeySpec decodePrivateKey(@NotNull final byte[] encodedPrivateKey) {
		return new PKCS8EncodedKeySpec(encodedPrivateKey);
	}

	protected KeyPairGenerator getKeyPairGenerator(final String algorythmName) {
		try {
			return KeyPairGenerator.getInstance(algorythmName);
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmRuntimeException(e);
		}
	}

	protected KeyFactory getKeyFactory(final String algorythmName) {
		try {
			return KeyFactory.getInstance(algorythmName);
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmRuntimeException(e);
		}
	}
}
