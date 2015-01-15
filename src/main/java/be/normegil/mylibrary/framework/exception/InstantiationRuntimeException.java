package be.normegil.mylibrary.framework.exception;

public class InstantiationRuntimeException extends RuntimeException{

	public InstantiationRuntimeException(final String message, final InstantiationException cause) {
		super(message, cause);
	}

	public InstantiationRuntimeException(final InstantiationException cause) {
		super(cause);
	}
}
