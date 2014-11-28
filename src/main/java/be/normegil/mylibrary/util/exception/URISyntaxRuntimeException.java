package be.normegil.mylibrary.util.exception;

public class URISyntaxRuntimeException extends RuntimeException {

    public URISyntaxRuntimeException(final String message, final java.net.URISyntaxException cause) {
        super(message, cause);
    }

    public URISyntaxRuntimeException(final java.net.URISyntaxException cause) {
        super(cause);
    }
}
