package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.parser.CsvParser;
import be.normegil.mylibrary.tools.CustomCollectors;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class UTErrorRepository {

	@Test
	public void testGet() throws Exception {
		ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
		List<RESTError> errors = new CsvParser<>(RESTError.class).from(
				getClass().getResourceAsStream("/RestErrors.csv"));

		Optional<RESTError> expected = errors.stream()
				.filter((e) -> e.getCode() == errorCode.getCode())
				.collect(new CustomCollectors().singletonCollector());

		Optional<RESTError> toTest = new ErrorRepository().get(errorCode.getCode());
		assertEquals(expected, toTest);
	}
}
