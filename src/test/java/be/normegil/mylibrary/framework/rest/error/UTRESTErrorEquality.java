package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.test.AbstractDataEqualityTest;
import org.junit.Test;

import java.net.URI;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UTRESTErrorEquality extends AbstractDataEqualityTest<RESTError> {

	private static final IGenerator<RESTError> GENERATOR = GeneratorRepository.get(RESTError.class);
	private static final IGenerator<URI> URI_GENERATOR = GeneratorRepository.get(URI.class);
	private static final int DEFAULT_HASHCODE = 1377548073;

	@Override
	protected RESTError getNewEntity() {
		return GENERATOR.getDefault(false, false);
	}

	@Test
	public void testUnchanged() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = new RESTError(entity);
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentCode() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setCode(entity.getCode() + 1)
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentStatus() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setHttpStatus(HttpStatus.ACCEPTED)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentMessage() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setMessage(entity.getMessage() + 1)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentDeveloperMessage() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setDeveloperMessage(entity.getDeveloperMessage() + 1)
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentMoreInfoURL() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setMoreInfoURL(URI_GENERATOR.getNew(false, false))
				.build();
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentTime() throws Exception {
		RESTError entity = getEntity();
		RESTError copy = RESTError.builder()
				.from(entity)
				.setTime(entity.getTime().plus(2, ChronoUnit.MINUTES))
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testHashcode() throws Exception {
		RESTError restError = GENERATOR.getDefault(false, false);
		assertEquals(DEFAULT_HASHCODE, restError.hashCode());
	}
}