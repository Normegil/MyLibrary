package be.normegil.mylibrary.framework.exception;

public class NoSuchMethodRuntimeException extends RuntimeException {

    public NoSuchMethodRuntimeException(final String message, final NoSuchMethodException cause) {
        super(message, cause);
    }

    public NoSuchMethodRuntimeException(final NoSuchMethodException cause) {
        super(cause);
    }
}
