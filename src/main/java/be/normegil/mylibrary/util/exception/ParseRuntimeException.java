package be.normegil.mylibrary.util.exception;

import java.text.ParseException;

public class ParseRuntimeException extends RuntimeException {
	public ParseRuntimeException(final String message, final ParseException cause) {
		super(message, cause);
	}

	public ParseRuntimeException(final ParseException cause) {
		super(cause);
	}
}
