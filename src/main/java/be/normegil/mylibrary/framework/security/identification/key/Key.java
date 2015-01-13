package be.normegil.mylibrary.util.security.identification.key;

import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.constraint.NotEmpty;
import be.normegil.mylibrary.util.exception.InvalidKeySpecRuntimeException;
import be.normegil.mylibrary.util.exception.NoSuchAlgorithmRuntimeException;

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

	public Key(final String name, final KeyType type) {
		super();
		this.name = name;
		this.type = type;

		KeyPair keyPair = getKeyPair(type);

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
			return getKetFactory()
					.generatePublic(decodePublicKey(encodedPublicKey));
		} catch (InvalidKeySpecException e) {
			throw new InvalidKeySpecRuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		try {
			return getKetFactory()
					.generatePrivate(decodePrivateKey(encodedPrivateKey));
		} catch (InvalidKeySpecException e) {
			throw new InvalidKeySpecRuntimeException(e);
		}
	}

	private KeyPair getKeyPair(final KeyType type) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(type.getAlgorythmName());
			generator.initialize(type.getDefaultKeySize(), new SecureRandom());
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmRuntimeException(e);
		}
	}

	public byte[] encodePublicKey(final byte[] publicKey) {
		return new X509EncodedKeySpec(publicKey).getEncoded();
	}

	public EncodedKeySpec decodePublicKey(final byte[] encodedPublicKey) {
		return new X509EncodedKeySpec(encodedPublicKey);
	}

	public byte[] encodePrivateKey(final byte[] privateKey) {
		return new PKCS8EncodedKeySpec(privateKey).getEncoded();
	}

	public EncodedKeySpec decodePrivateKey(final byte[] encodedPrivateKey) {
		return new PKCS8EncodedKeySpec(encodedPrivateKey);
	}

	private KeyFactory getKetFactory() {
		try {
			return KeyFactory.getInstance(getType().getAlgorythmName());
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmRuntimeException(e);
		}
	}
}
