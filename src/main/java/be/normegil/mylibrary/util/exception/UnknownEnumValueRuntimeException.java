package be.normegil.mylibrary.util.exception;

public class UnknownEnumValueRuntimeException extends RuntimeException {

	public UnknownEnumValueRuntimeException(final String message) {
		super(message);
	}

	public UnknownEnumValueRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
