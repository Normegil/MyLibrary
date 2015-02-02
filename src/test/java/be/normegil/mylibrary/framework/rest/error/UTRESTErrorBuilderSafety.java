package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDateTime;

public class UTRESTErrorBuilderSafety {

	private static final ClassWrapper<RESTError.Builder> CLASS = new ClassWrapper<>(RESTError.Builder.class);
	private static final String EMPTY_STRING = "";
	private RESTError.Builder entity;

	@Before
	public void setUp() throws Exception {
		entity = RESTError.builder();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testEmptyBuildReturnValue() throws Exception {
		Validator.validate(entity, CLASS.getMethod("build"));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFrom_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("from", RESTError.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetStatus_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setHttpStatus", HttpStatus.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetMessage_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setMessage", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetMessage_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setMessage", String.class), EMPTY_STRING);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetDeveloperMessage_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setDeveloperMessage", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetDeveloperMessage_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setDeveloperMessage", String.class), EMPTY_STRING);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetMoreInfoUrl_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setMoreInfoURL", URI.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetTime_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setTime", LocalDateTime.class), new Object[]{null});
	}
}