package be.normegil.mylibrary.util.rest.error;

import be.normegil.mylibrary.util.parser.CsvParser;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class ErrorRepository {

	public Optional<RESTError> get(int code) {
		InputStream stream = getClass().getResourceAsStream("/RestErrors.csv");
		List<RESTError> restErrors = new CsvParser<>(RESTError.class).from(stream);
		Optional<RESTError> error = restErrors.stream()
				.filter(e -> e.getCode() == code)
				.findFirst();
		return error;
	}

}
