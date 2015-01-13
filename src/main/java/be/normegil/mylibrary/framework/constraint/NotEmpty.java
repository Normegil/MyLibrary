package be.normegil.mylibrary.framework.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
		ElementType.FIELD,
		ElementType.PARAMETER,
		ElementType.METHOD,
		ElementType.ANNOTATION_TYPE,
		ElementType.LOCAL_VARIABLE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyValidator.class)
public @interface NotEmpty {

	String message() default
			"Given argument cannot be null/empty";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
