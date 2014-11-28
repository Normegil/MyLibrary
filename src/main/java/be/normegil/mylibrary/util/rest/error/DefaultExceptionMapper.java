package be.normegil.mylibrary.util.rest.error;

import be.normegil.mylibrary.util.exception.ErrorNotFoundException;
import be.normegil.mylibrary.util.exception.WebApplicationException;
import be.normegil.mylibrary.util.rest.HttpStatus;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.Optional;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	public static final int DEFAULT_ERROR_CODE = 5000;
	@Inject
	private transient Logger log;

	@Override
	public Response toResponse(final Throwable exception) {
		log.error(exception.getMessage(), exception);

		int errorCode = exception instanceof WebApplicationException ?
				((WebApplicationException) exception).getErrorCode().getCode() :
				DEFAULT_ERROR_CODE;

		ErrorRepository repository = new ErrorRepository();
		Optional<RESTError> restErrorOptional = repository.get(errorCode);
		RESTError restError = restErrorOptional
				.orElse(repository.get(DEFAULT_ERROR_CODE)
								.orElseThrow(ErrorNotFoundException::new)
				);

		RESTError restErrorWithTime = restError.withTime(LocalDateTime.now());

		return Response
				.ok(restErrorWithTime)
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.build();
	}
}
