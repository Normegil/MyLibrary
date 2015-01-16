package be.normegil.mylibrary.framework.constraint;

import be.normegil.mylibrary.framework.DateHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UTValidDateFormatValidator {

	public static final String WRONG_FORMATTED_DATE = "2015-10-14 08:45";
	public static final String VALID_DATE_FORMAT = "2015-01-16T09:31:11.045Z";
	@Mock
	private ConstraintValidatorContext context;

	private ValidDateFormatValidator validator = new ValidDateFormatValidator();

	@Test
	public void testIsValid_NullDate() throws Exception {
		assertFalse(validator.isValid(null, context));
	}

	@Test
	public void testIsValid_NullContext() throws Exception {
		assertTrue(validator.isValid(VALID_DATE_FORMAT, null));

	}

	@Test
	public void testIsValid_EmptyString() throws Exception {
		assertFalse(validator.isValid("", context));
	}

	@Test
	public void testIsValid_WrongFormat() throws Exception {
		assertFalse(validator.isValid(WRONG_FORMATTED_DATE, context));
	}

	@Test
	public void testIsValid_ValidFormat() throws Exception {
		assertTrue(validator.isValid(VALID_DATE_FORMAT, context));
	}
}
