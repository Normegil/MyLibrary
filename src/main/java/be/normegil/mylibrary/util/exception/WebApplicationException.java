package be.normegil.mylibrary.util.exception;

import be.normegil.mylibrary.util.rest.error.ErrorCode;

public interface WebApplicationException {

	ErrorCode getErrorCode();

}
