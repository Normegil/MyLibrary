package be.normegil.mylibrary.util.exception;

public class RESTServiceNotFoundException extends RuntimeException{

	public RESTServiceNotFoundException(final String message) {
		super(message);
	}

	public RESTServiceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
