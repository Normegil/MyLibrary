package be.normegil.mylibrary.framework.exception;

public class InvocationTargetRuntimeException extends RuntimeException {

    public InvocationTargetRuntimeException(final String message, final java.lang.reflect.InvocationTargetException e) {
        super(message, e);
    }

    public InvocationTargetRuntimeException(final java.lang.reflect.InvocationTargetException e) {
        super(e);
    }
}
