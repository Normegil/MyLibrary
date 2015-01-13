package be.normegil.mylibrary.framework.exception;

public class ReadOnlyRuntimeException extends RuntimeException{

	public ReadOnlyRuntimeException(final String message) {
		super(message);
	}

	public ReadOnlyRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ReadOnlyRuntimeException(final Throwable cause) {
		super(cause);
	}
}
