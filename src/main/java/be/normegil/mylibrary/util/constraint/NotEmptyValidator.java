package be.normegil.mylibrary.util.constraint;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
	@Override
	public void initialize(final NotEmpty notEmpty) {
	}

	@Override
	public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
		return !StringUtils.isBlank(s);
	}
}
