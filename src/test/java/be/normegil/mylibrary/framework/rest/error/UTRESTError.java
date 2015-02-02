package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UTRESTError {

	private static final IGenerator<RESTError> GENERATOR = GeneratorRepository.get(RESTError.class);
	private RESTError entity;

	@Before
	public void setUp() throws Exception {
		entity = GENERATOR.getDefault(false, false);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testCopyConstructor() throws Exception {
		RESTError copy = new RESTError(entity);
		assertEquals(entity, copy);
	}

	@Test
	public void testWithTime() throws Exception {
		LocalDateTime newTime = entity.getTime().plus(2, ChronoUnit.MINUTES);
		RESTError copy = entity.withTime(newTime);
		assertEquals(newTime, copy.getTime());
	}

	@Test
	public void testWithTime_Immutable() throws Exception {
		LocalDateTime time = entity.getTime();
		LocalDateTime newTime = entity.getTime().plus(2, ChronoUnit.MINUTES);
		entity.withTime(newTime);
		assertEquals(time, entity.getTime());
	}

	@Test
	public void testToString() throws Exception {
		RESTError error = GENERATOR.getNew(false, false);
		assertFalse(StringUtils.isBlank(error.toString()));
	}
}
