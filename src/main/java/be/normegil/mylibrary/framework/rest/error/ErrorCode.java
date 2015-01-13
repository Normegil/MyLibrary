package be.normegil.mylibrary.framework.rest.error;

public enum ErrorCode {
	ID_NULL(40000),
	INVALID_URI(40001),

	BAD_AUTHENTICATION_REQUEST(40100),
	AUTHENTICATION_USER_NOT_FOUND(40101),
	AUTHENTICATION_WRONG_PASSWORD(40102),
	AUTHENTICATION_INVALID_TOKEN(40103),

	ACCESS_DENIED(40300),

	OBJECT_NOT_FOUND(40400);

	private final int errorCode;

	ErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public int getCode() {
		return errorCode;
	}
}
