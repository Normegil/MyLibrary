package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTCoupleSafety {

	public static final ClassWrapper<Couple> CLASS = new ClassWrapper<>(Couple.class);

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullX() throws Exception {
		Validator.validate(CLASS.getConstructor(Object.class, Object.class), new Object[]{null, "Test"});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullY() throws Exception {
		Validator.validate(CLASS.getConstructor(Object.class, Object.class), "Test", null);
	}

}
