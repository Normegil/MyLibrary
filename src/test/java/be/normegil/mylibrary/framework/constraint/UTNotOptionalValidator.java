package be.normegil.mylibrary.framework.constraint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UTNotOptionalValidator {

	public static final Object VALID_OBJECT = "String";
	private NotOptionalValidator validator = new NotOptionalValidator();

	@Mock
	private ConstraintValidatorContext context;

	@Test
	public void testIsValid_NullObject() throws Exception {
		assertTrue(validator.isValid(null, context));
	}

	@Test
	public void testIsValid_NullContext() throws Exception {
		assertTrue(validator.isValid(VALID_OBJECT, null));
	}

	@Test
	public void testIsValid_NotOptional() throws Exception {
		assertTrue(validator.isValid(VALID_OBJECT, context));
	}

	@Test
	public void testIsValid_Optional() throws Exception {
		assertFalse(validator.isValid(Optional.of(VALID_OBJECT), context));
	}
}
