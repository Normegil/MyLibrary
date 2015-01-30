package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.net.URI;

public class UTCollectionResourceHelperSafety {

	private static final ClassWrapper<CollectionResource.Helper> CLASS = new ClassWrapper<>(CollectionResource.Helper.class);
	private CollectionResource.Helper entity;

	@Before
	public void setUp() throws Exception {
		entity = CollectionResource.helper();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetCollectionURI_NullURI() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getCollectionURI", URI.class, long.class, int.class), null, 0L, ApplicationProperties.REST.DEFAULT_LIMIT);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetBaseURI_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getBaseURI", URI.class), new Object[]{null});
	}
}
