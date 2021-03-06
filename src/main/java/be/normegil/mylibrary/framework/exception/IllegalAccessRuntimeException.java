package be.normegil.mylibrary.framework.exception;

public class IllegalAccessRuntimeException extends RuntimeException {

    public IllegalAccessRuntimeException(final String message, final IllegalAccessException e) {
        super(message, e);
    }

    public IllegalAccessRuntimeException(final IllegalAccessException e) {
        super(e);
    }
}
