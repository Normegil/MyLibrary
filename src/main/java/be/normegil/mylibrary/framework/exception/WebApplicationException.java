package be.normegil.mylibrary.framework.exception;

import be.normegil.mylibrary.framework.rest.error.ErrorCode;

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
