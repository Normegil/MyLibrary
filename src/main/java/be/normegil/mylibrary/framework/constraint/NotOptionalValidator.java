package be.normegil.mylibrary.framework.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NotOptionalValidator implements ConstraintValidator<NotOptional, Object> {
	@Override
	public void initialize(final NotOptional constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		return !(value instanceof Optional);
	}
}
