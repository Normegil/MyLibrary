package be.normegil.mylibrary.util.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
		ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvSchema {

	boolean DEFAULT_USE_HEADER = true;
	boolean DEFAULT_READ_ONLY = false;

	boolean useHeaders() default DEFAULT_USE_HEADER;

	boolean readOnly() default DEFAULT_READ_ONLY;

	String[] columns() default {};

}
