package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.HttpStatus;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class UTDefaultExceptionMapper {

	private DefaultExceptionMapper defaultExceptionMapper = new DefaultExceptionMapper();

	@Test
	public void testToResponse_DefaultException_Status() throws Exception {
		Response response = defaultExceptionMapper.toResponse(new IllegalStateException(""));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	}

	@Test
	public void testToResponse_DefaultException_RestError() throws Exception {
		Response response = defaultExceptionMapper.toResponse(new IllegalStateException(""));
		RESTError toTest = (RESTError) response.getEntity();
		Optional<RESTError> restErrorOptional = new ErrorRepository().get(DefaultExceptionMapper.DEFAULT_ERROR_CODE);
		RESTError expected = RESTError.builder().from(restErrorOptional.get())
				.setTime(toTest.getTime())
				.build();
		assertEquals(expected, toTest);
	}

	@Test
	public void testToResponse_Status() throws Exception {
		Response response = defaultExceptionMapper.toResponse(new WebApplicationException(ErrorCode.ACCESS_DENIED, new IllegalStateException("")));
		assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	}

	@Test
	public void testToResponse_RestError() throws Exception {
		Response response = defaultExceptionMapper.toResponse(new WebApplicationException(ErrorCode.ACCESS_DENIED, new IllegalStateException("")));
		RESTError toTest = (RESTError) response.getEntity();
		Optional<RESTError> restErrorOptional = new ErrorRepository().get(ErrorCode.ACCESS_DENIED.getCode());
		RESTError expected = RESTError.builder().from(restErrorOptional.get())
				.setTime(toTest.getTime())
				.build();
		assertEquals(expected, toTest);
	}
}
