package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTRESTErrorSafety {

	private static final IGenerator<RESTError> GENERATOR = GeneratorRepository.get(RESTError.class);
	private static final ClassWrapper<RESTError> CLASS = new ClassWrapper<>(RESTError.class);
	private RESTError entity;

	@Before
	public void setUp() throws Exception {
		entity = GENERATOR.getDefault(false, false);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testBuilderConstructor_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(RESTError.Builder.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCopyConstructor_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(RESTError.class), new Object[]{null});
	}
}