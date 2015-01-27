package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTKeyManagerSafety {

	private static final ClassWrapper<KeyManager> CLASS = new ClassWrapper<>(KeyManager.class);

	private KeyManager entity = new KeyManager();

	@Test(expected = ConstraintViolationException.class)
	public void testLoad_NullName() throws Exception {
		Validator.validate(entity, CLASS.getMethod("load", String.class, KeyType.class), null, KeyType.ECDSA);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testLoad_EmptyName() throws Exception {
		Validator.validate(entity, CLASS.getMethod("load", String.class, KeyType.class), "", KeyType.ECDSA);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testLoad_NullKeyType() throws Exception {
		Validator.validate(entity, CLASS.getMethod("load", String.class, KeyType.class), "Test", null);
	}

}
