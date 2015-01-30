package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTCollectionResourceSafety {

	private static final ClassWrapper<CollectionResource> CLASS = new ClassWrapper<>(CollectionResource.class);

	@Test(expected = ConstraintViolationException.class)
	public void testCopyConstructor_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(CollectionResource.class), new Object[]{null});
	}

}
