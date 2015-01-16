package be.normegil.mylibrary.framework.constraint;


import be.normegil.mylibrary.framework.Entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingIDValidator implements ConstraintValidator<ExistingID, Entity> {
	@Override
	public void initialize(final ExistingID constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Entity value, final ConstraintValidatorContext context) {
		return value != null && value.getId().isPresent();
	}
}
