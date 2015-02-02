package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class UTRESTErrorBuilder {

	private static final IGenerator<RESTError> GENERATOR = GeneratorRepository.get(RESTError.class);
	private static final IGenerator<URI> URI_GENERATOR = GeneratorRepository.get(URI.class);
	private static final int ERROR_CODE = 34567;
	private static final String MESSAGE = "Message";
	private static final String DEVELOPER_MESSAGE = "DeveloperMessage";
	private static final IllegalArgumentException THROWABLE = new IllegalArgumentException();
	private RESTError.Builder entity;

	@Before
	public void setUp() throws Exception {
		entity = RESTError.builder();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testFrom_Code() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getCode(), copy.getCode());
	}

	@Test
	public void testFrom_Status() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getHttpStatus(), copy.getHttpStatus());
	}

	@Test
	public void testFrom_Message() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getMessage(), copy.getMessage());
	}

	@Test
	public void testFrom_DeveloperMessage() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getDeveloperMessage(), copy.getDeveloperMessage());
	}

	@Test
	public void testFrom_URL() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getMoreInfoUri(), copy.getMoreInfoUri());
	}

	@Test
	public void testFrom_Time() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getTime(), copy.getTime());
	}

	@Test
	public void testSetCode() throws Exception {
		RESTError rESTError = entity
				.setCode(34567)
				.build();
		assertEquals(ERROR_CODE, rESTError.getCode());
	}

	@Test
	public void testSetHttpStatus() throws Exception {
		RESTError restError = entity
				.setHttpStatus(HttpStatus.ACCEPTED)
				.build();
		assertEquals(HttpStatus.ACCEPTED.value(), restError.getHttpStatus());
	}

	@Test
	public void testSetMessage() throws Exception {
		RESTError rESTError = entity
				.setMessage(MESSAGE)
				.build();
		assertEquals(MESSAGE, rESTError.getMessage());
	}

	@Test
	public void testSetDeveloperMessage() throws Exception {
		RESTError rESTError = entity
				.setDeveloperMessage(DEVELOPER_MESSAGE)
				.build();
		assertEquals(DEVELOPER_MESSAGE, rESTError.getDeveloperMessage());
	}

	@Test
	public void testSetMoreInfoURL() throws Exception {
		URI uri = URI_GENERATOR.getNew(false, false);
		RESTError rESTError = entity
				.setMoreInfoURL(uri)
				.build();
		assertEquals(uri, rESTError.getMoreInfoUri());
	}

	@Test
	public void testSetTime() throws Exception {
		LocalDateTime time = LocalDateTime.now();
		RESTError rESTError = entity
				.setTime(time)
				.build();
		assertEquals(time, rESTError.getTime());
	}
}