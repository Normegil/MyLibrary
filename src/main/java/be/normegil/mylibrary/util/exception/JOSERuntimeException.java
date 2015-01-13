package be.normegil.mylibrary.util.exception;

import com.nimbusds.jose.JOSEException;

public class JOSERuntimeException extends RuntimeException {
	public JOSERuntimeException(final String message, final JOSEException cause) {
		super(message, cause);
	}

	public JOSERuntimeException(final JOSEException cause) {
		super(cause);
	}
}
