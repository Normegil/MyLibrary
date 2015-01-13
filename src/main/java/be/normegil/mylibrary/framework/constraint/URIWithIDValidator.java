package be.normegil.mylibrary.framework.constraint;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URI;
import java.util.UUID;

public class URIWithIDValidator implements ConstraintValidator<URIWithID, URI> {
	@Override
	public void initialize(final URIWithID constraintAnnotation) {
	}

	@Override
	public boolean isValid(final URI value, final ConstraintValidatorContext context) {
		if (value != null) {
			String id = StringUtils.substringAfterLast(value.toString(), "/");
			try {
				UUID uuid = UUID.fromString(id);
				return uuid != null;
			} catch (IllegalArgumentException e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
