package be.normegil.mylibrary.framework.exception;

public class UnknownEnumValueRuntimeException extends RuntimeException {

	public UnknownEnumValueRuntimeException(final String message) {
		super(message);
	}

	public UnknownEnumValueRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
