package be.normegil.mylibrary.framework.exception;

import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTWebApplicationException {

	public static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.AUTHENTICATION_INVALID_TOKEN;

	@Test
	public void testGetErrorCode() throws Exception {
		WebApplicationException exception = new WebApplicationException(DEFAULT_ERROR_CODE, new IllegalArgumentException());
		assertEquals(DEFAULT_ERROR_CODE, exception.getErrorCode());
	}
}
