package be.normegil.mylibrary.util.rest.error;

public enum ErrorCode {
	OBJECT_NOT_FOUND(4040),
	ID_NULL(4000);

	private final int errorCode;

	ErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

	public int getCode() {
		return errorCode;
	}
}
