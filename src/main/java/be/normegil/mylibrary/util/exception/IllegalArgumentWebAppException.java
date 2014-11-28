package be.normegil.mylibrary.util.exception;

import be.normegil.mylibrary.util.rest.error.ErrorCode;

public class IllegalArgumentWebAppException extends IllegalArgumentException implements WebApplicationException {

	private ErrorCode errorCode;

	public IllegalArgumentWebAppException(final ErrorCode errorCode, final String s) {
		super(s);
		this.errorCode = errorCode;
	}

	public IllegalArgumentWebAppException(final ErrorCode errorCode, final Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public IllegalArgumentWebAppException(final ErrorCode errorCode, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	@Override
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
