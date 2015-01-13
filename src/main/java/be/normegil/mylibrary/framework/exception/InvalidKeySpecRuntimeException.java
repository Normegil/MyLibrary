package be.normegil.mylibrary.framework.exception;

import java.security.spec.InvalidKeySpecException;

public class InvalidKeySpecRuntimeException extends RuntimeException {

	public InvalidKeySpecRuntimeException(final String message, final InvalidKeySpecException cause) {
		super(message, cause);
	}

	public InvalidKeySpecRuntimeException(final InvalidKeySpecException cause) {
		super(cause);
	}
}
