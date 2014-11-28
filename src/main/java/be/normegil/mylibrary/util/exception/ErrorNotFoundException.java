package be.normegil.mylibrary.util.exception;

public class ErrorNotFoundException extends RuntimeException{

	public ErrorNotFoundException() {
	}

	public ErrorNotFoundException(final String message) {
		super(message);
	}

	public ErrorNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ErrorNotFoundException(final Throwable cause) {
		super(cause);
	}
}
