package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.exception.ErrorNotFoundException;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.Optional;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	public static final int DEFAULT_ERROR_CODE = 50000;

	@Override
	public Response toResponse(final Throwable throwable) {
		int errorCode = getErrorCode(throwable);
		RESTError restError = getRestError(errorCode);

		RESTError restErrorWithTime = restError.withTime(LocalDateTime.now());
		return Response
				.ok(restErrorWithTime)
				.status(restErrorWithTime.getHttpStatus())
				.build();
	}

	private RESTError getRestError(final int errorCode) {
		ErrorRepository repository = new ErrorRepository();
		Optional<RESTError> restErrorOptional = repository.get(errorCode);
		return restErrorOptional
				.orElse(repository.get(DEFAULT_ERROR_CODE)
								.orElseThrow(ErrorNotFoundException::new)
				);
	}

	private int getErrorCode(final Throwable throwable) {
		int errorCode;
		Optional<WebApplicationException> webApplicationExceptionOptional = getWebApplicationException(throwable);
		if (webApplicationExceptionOptional.isPresent()) {
			WebApplicationException webApplicationException = webApplicationExceptionOptional.get();
			ErrorCode errorCodeEnum = webApplicationException.getErrorCode();
			errorCode = errorCodeEnum.getCode();
		} else {
			errorCode = DEFAULT_ERROR_CODE;
		}
		return errorCode;
	}

	private Optional<WebApplicationException> getWebApplicationException(final Throwable throwable) {
		if (throwable == null) {
			return Optional.empty();
		} else if (throwable instanceof WebApplicationException) {
			return Optional.of((WebApplicationException) throwable);
		} else {
			return getWebApplicationException(throwable.getCause());
		}
	}
}
