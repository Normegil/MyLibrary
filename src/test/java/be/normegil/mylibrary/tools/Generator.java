package be.normegil.mylibrary.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Generator {

	/**
	 * Supported class
	 */
	Class value();

}
