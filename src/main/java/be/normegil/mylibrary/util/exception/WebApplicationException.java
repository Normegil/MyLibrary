package be.normegil.mylibrary.util.exception;

import be.normegil.mylibrary.util.rest.error.ErrorCode;

public class WebApplicationException extends RuntimeException {

	private final ErrorCode errorCode;

	public WebApplicationException(final ErrorCode errorCode, final Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
