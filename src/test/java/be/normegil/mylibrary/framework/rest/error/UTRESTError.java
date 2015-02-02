package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class UTRESTError {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<RESTError> FACTORY = FactoryRepository.get(RESTError.class);
	private RESTError entity;

	@Before
	public void setUp() throws Exception {
		entity = FACTORY.getDefault();
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
}
