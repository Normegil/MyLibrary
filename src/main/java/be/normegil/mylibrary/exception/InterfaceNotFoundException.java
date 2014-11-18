package be.normegil.mylibrary.exception;

public class InterfaceNotFoundException extends RuntimeException{
	public InterfaceNotFoundException(final String message) {
		super(message);
	}

	public InterfaceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
