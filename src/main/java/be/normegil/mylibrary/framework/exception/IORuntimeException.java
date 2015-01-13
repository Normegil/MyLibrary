package be.normegil.mylibrary.framework.exception;

public class IORuntimeException extends RuntimeException {

    public IORuntimeException(final String message, final java.io.IOException cause) {
        super(message, cause);
    }

    public IORuntimeException(final java.io.IOException cause) {
        super(cause);
    }

	public IORuntimeException(final String message) {
		super(message);
	}
}
