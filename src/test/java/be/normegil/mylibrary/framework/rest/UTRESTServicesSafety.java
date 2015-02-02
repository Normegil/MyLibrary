package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.exception.RESTServiceNotFoundException;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTRESTServicesSafety {

	private static final ClassWrapper<RESTServices> CLASS = new ClassWrapper<>(RESTServices.class);
	private RESTServices entity = new RESTServices();

	@Test(expected = ConstraintViolationException.class)
	public void testGetPathForResourceType_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getPathForResourceType", Class.class), new Object[]{null});
	}

	@Test(expected = RESTServiceNotFoundException.class)
	public void testGetPathForResourceType_ServiceNotFound() throws Exception {
		entity.getPathForResourceType(User.class);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetPathFor_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getPathFor", Class.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetServicesForResourceType_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getServicesForResourceType", Class.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetServicesFor_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getServicesFor", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetServicesFor_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getServicesFor", String.class), "");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetDefaultServiceFor_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getDefaultServiceFor", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetDefaultServiceFor_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getDefaultServiceFor", String.class), "");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetDefaultServiceFor_Class_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getDefaultServiceFor", Class.class), new Object[]{null});
	}
}
