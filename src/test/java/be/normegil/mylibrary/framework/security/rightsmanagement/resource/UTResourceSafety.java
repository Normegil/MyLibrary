package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTResourceSafety {

	private static final ClassWrapper<Resource> CLASS = new ClassWrapper<>(Resource.class);

	@Test(expected = ConstraintViolationException.class)
	public void test_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class), new Object[]{null});
	}
}
