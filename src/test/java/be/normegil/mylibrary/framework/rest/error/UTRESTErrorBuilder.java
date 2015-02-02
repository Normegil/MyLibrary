package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.libraries.URL;
import be.normegil.librarium.model.rest.HttpStatus;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class UTRESTErrorBuilder {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<RESTError> REST_ERROR_FACTORY = FactoryRepository.get(RESTError.class);
	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<URL> URL_FACTORY = FactoryRepository.get(URL.class);
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
		RESTError error = REST_ERROR_FACTORY.getNew();
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getCode(), copy.getCode());
	}

	@Test
	public void testFrom_Status() throws Exception {
		RESTError error = REST_ERROR_FACTORY.getNew();
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getHttpStatus(), copy.getHttpStatus());
	}

	@Test
	public void testFrom_Message() throws Exception {
		RESTError error = REST_ERROR_FACTORY.getNew();
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getMessage(), copy.getMessage());
	}

	@Test
	public void testFrom_DeveloperMessage() throws Exception {
		RESTError error = REST_ERROR_FACTORY.getNew();
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getDeveloperMessage(), copy.getDeveloperMessage());
	}

	@Test
	public void testFrom_URL() throws Exception {
		RESTError error = REST_ERROR_FACTORY.getNew();
		RESTError copy = entity
				.from(error)
				.build();
		assertEquals(error.getMoreInfoURL(), copy.getMoreInfoURL());
	}

	@Test
	public void testFrom_Time() throws Exception {
		RESTError error = REST_ERROR_FACTORY.getNew();
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
		RESTError rESTError = entity
				.setHttpStatus(HttpStatus.ACCEPTED)
				.build();
		assertEquals(HttpStatus.ACCEPTED, rESTError.getHttpStatus());
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
		URL url = URL_FACTORY.getNew();
		RESTError rESTError = entity
				.setMoreInfoURL(url)
				.build();
		assertEquals(url, rESTError.getMoreInfoURL());
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