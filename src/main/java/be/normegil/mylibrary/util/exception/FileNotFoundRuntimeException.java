package be.normegil.mylibrary.util.exception;

import java.io.FileNotFoundException;

public class FileNotFoundRuntimeException extends RuntimeException {

	public FileNotFoundRuntimeException(final FileNotFoundException cause) {
		super(cause);
	}

	public FileNotFoundRuntimeException(final String message, final FileNotFoundException cause) {
		super(message, cause);
	}
}
