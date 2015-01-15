package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.net.URI;

public class UTURIHelperSafety {

	private static final ClassWrapper<URIHelper> CLASS = new ClassWrapper<>(URIHelper.class);
	private URIHelper entity = new URIHelper();

	@Test(expected = ConstraintViolationException.class)
	public void testRemoveParameters_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("removeParameters", URI.class), new Object[]{null});
	}

}
