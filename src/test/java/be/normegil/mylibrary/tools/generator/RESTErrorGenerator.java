package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.framework.rest.error.RESTError;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.IGenerator;

import java.net.URI;
import java.time.LocalDateTime;

@Generator(RESTError.class)
public class RESTErrorGenerator implements IGenerator<RESTError> {

	private static final int DEFAULT_CODE = 40400;
	private static final String DEFAULT_MESSAGE = "Message";
	private static final String DEFAULT_URI = "http://www.example.com/rest/error/";
	private static final String DEFAULT_DEV_MESSAGE = "DevMessage";
	private static long index = 0L;

	@Override
	public RESTError getDefault(final boolean withLink, final boolean withIds) {
		return RESTError.builder()
				.setCode(DEFAULT_CODE)
				.setHttpStatus(HttpStatus.NOT_FOUND)
				.setMessage(DEFAULT_MESSAGE)
				.setDeveloperMessage(DEFAULT_DEV_MESSAGE)
				.setTime(LocalDateTime.of(2015, 2, 2, 16, 24))
				.setMoreInfoURL(URI.create(DEFAULT_URI + "40400"))
				.build();
	}

	@Override
	public RESTError getNew(final boolean withLink, final boolean withIds) {
		long index = getIndex();
		int code = (int) (DEFAULT_CODE + index);
		return RESTError.builder()
				.setCode(code)
				.setHttpStatus(HttpStatus.NOT_FOUND)
				.setMessage(DEFAULT_MESSAGE + index)
				.setDeveloperMessage(DEFAULT_MESSAGE + index)
				.setTime(LocalDateTime.now())
				.setMoreInfoURL(URI.create(DEFAULT_URI + code))
				.build();
	}

	public synchronized long getIndex() {
		index += 1;
		return index;
	}
}
