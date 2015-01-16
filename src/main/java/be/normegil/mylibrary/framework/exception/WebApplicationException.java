package be.normegil.mylibrary.framework.exception;

import be.normegil.mylibrary.framework.rest.error.ErrorCode;

import javax.validation.constraints.NotNull;

public class WebApplicationException extends RuntimeException {

	private final ErrorCode errorCode;

	public WebApplicationException(@NotNull final ErrorCode errorCode, @NotNull final Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
