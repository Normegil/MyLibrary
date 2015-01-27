package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.exception.InvalidKeySpecRuntimeException;
import be.normegil.mylibrary.framework.exception.NoSuchAlgorithmRuntimeException;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import be.normegil.mylibrary.tools.generator.KeyGenerator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class UTKeySafety {

	private static final ClassWrapper<Key> CLASS = new ClassWrapper<>(Key.class);

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullName() throws Exception {
		Validator.validate(CLASS.getConstructor(String.class, KeyType.class), new Object[]{null, KeyType.ECDSA});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_EmptyName() throws Exception {
		Validator.validate(CLASS.getConstructor(String.class, KeyType.class), "", KeyType.ECDSA);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullKeyType() throws Exception {
		Validator.validate(CLASS.getConstructor(String.class, KeyType.class), "Test", null);
	}

	@Test(expected = InvalidKeySpecRuntimeException.class)
	public void testGetPublicKey_InvalidKeySpecException() throws Exception {
		Key key = new Key(KeyGenerator.KEY_NAME, KeyGenerator.KEY_TYPE) {
			@Override
			protected EncodedKeySpec decodePublicKey(@NotNull final byte[] encodedPublicKey) {
				return new PKCS8EncodedKeySpec(getPrivateKey().getEncoded());
			}
		};
		key.getPublicKey();
	}

	@Test(expected = InvalidKeySpecRuntimeException.class)
	public void testGetPrivateKey_InvalidKeySpecException() throws Exception {
		Key key = new Key(KeyGenerator.KEY_NAME, KeyGenerator.KEY_TYPE) {
			@Override
			protected EncodedKeySpec decodePrivateKey(@NotNull final byte[] encodedPrivateKey) {
				return new X509EncodedKeySpec(getPublicKey().getEncoded());
			}
		};
		key.getPrivateKey();
	}

	@Test(expected = NoSuchAlgorithmRuntimeException.class)
	public void testGetKeyFactory_NoSuchAlgorithmException() throws Exception {
		Key key = new Key(KeyGenerator.KEY_NAME, KeyType.ECDSA);
		key.getKeyFactory("FakeAlgorythm");
	}

	@Test(expected = NoSuchAlgorithmRuntimeException.class)
	public void testGetKeyPairGenerator_NoSuchAlgorithmException() throws Exception {
		Key key = new Key(KeyGenerator.KEY_NAME, KeyType.ECDSA);
		key.getKeyPairGenerator("FakeAlgorythm");
	}
}
