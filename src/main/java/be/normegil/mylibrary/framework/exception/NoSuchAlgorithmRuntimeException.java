package be.normegil.mylibrary.framework.exception;

import java.security.NoSuchAlgorithmException;

public class NoSuchAlgorithmRuntimeException extends RuntimeException{

	public NoSuchAlgorithmRuntimeException(final String message, final NoSuchAlgorithmException cause) {
		super(message, cause);
	}

	public NoSuchAlgorithmRuntimeException(final NoSuchAlgorithmException cause) {
		super(cause);
	}
}
