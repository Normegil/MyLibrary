package be.normegil.mylibrary.framework.constraint;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UTNotEmptyValidator {

	private static final String EMPTY_STRING = "";
	private static final String NOT_EMPTY_STRING = "Test";
	public static final String SPACE_STRING = "    ";
	private NotEmptyValidator entity = new NotEmptyValidator();
	private ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(null, null, null);

	@Test
	public void testNull() throws Exception {
		assertFalse(entity.isValid(null, context));
	}

	@Test
	public void testEmpty() throws Exception {
		assertFalse(entity.isValid(EMPTY_STRING, context));
	}

	@Test
	public void testSpaceOnly() throws Exception {
		assertFalse(entity.isValid(SPACE_STRING, context));
	}

	@Test
	public void testNotEmpty() throws Exception {
		assertTrue(entity.isValid(NOT_EMPTY_STRING, context));
	}
}
