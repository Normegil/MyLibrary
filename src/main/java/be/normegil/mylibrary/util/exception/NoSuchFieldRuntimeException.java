package be.normegil.mylibrary.util.exception;

public class NoSuchFieldRuntimeException extends RuntimeException {

	public NoSuchFieldRuntimeException(final String message, final NoSuchFieldException cause) {
		super(message, cause);
	}

	public NoSuchFieldRuntimeException(final NoSuchFieldException cause) {
		super(cause);
	}

	public NoSuchFieldRuntimeException(final String message) {
		super(message);
	}
}
