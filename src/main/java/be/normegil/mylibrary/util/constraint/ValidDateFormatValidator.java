package be.normegil.mylibrary.util.constraint;

import be.normegil.mylibrary.ApplicationProperties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeParseException;

public class ValidDateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {
	@Override
	public void initialize(final ValidDateFormat validDateFormat) {
	}

	@Override
	public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
		if (s != null) {
			try {
				ApplicationProperties.STANDARD_TIME_FORMAT.parse(s);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
