package be.normegil.mylibrary.framework.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
		ElementType.FIELD,
		ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingIDValidator.class)
public @interface ExistingID {

	String message() default
			"Given argument has a null ID";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
