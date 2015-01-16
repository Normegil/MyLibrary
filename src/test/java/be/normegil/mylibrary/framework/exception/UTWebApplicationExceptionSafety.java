package be.normegil.mylibrary.framework.exception;

import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTWebApplicationExceptionSafety {

	private static final ClassWrapper<WebApplicationException> CLASS = new ClassWrapper<>(WebApplicationException.class);

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullErrorCode() throws Exception {
		Throwable throwable = new IllegalAccessException();
		Validator.validate(CLASS.getConstructor(ErrorCode.class, Throwable.class), new Object[]{null, throwable});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullException() throws Exception {
		Validator.validate(CLASS.getConstructor(ErrorCode.class, Throwable.class), ErrorCode.ACCESS_DENIED, null);
	}
}
