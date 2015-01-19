package be.normegil.mylibrary.framework.parser.adapter;

import be.normegil.mylibrary.framework.DateHelper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UTLocalDateTimeJsonSerializer {

	public static final String EMPTY_STRING = "";
	private DateHelper helper = new DateHelper();
	private LocalDateTimeJsonSerializer entity = new LocalDateTimeJsonSerializer();

	@Mock
	private JsonGenerator generator;

	@Mock
	private SerializerProvider serializerProvider;

	@Test(expected = IllegalArgumentException.class)
	public void testSerialize_NullGenerator() throws Exception {
		entity.serialize(LocalDateTime.now(), null, serializerProvider);
	}

	@Test
	public void testSerialize_NullDate() throws Exception {
		entity.serialize(null, generator, serializerProvider);
		assertGeneratedStringWas(EMPTY_STRING);
	}

	@Test
	public void testSerialize_NullProvider() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		entity.serialize(now, generator, serializerProvider);
		assertGeneratedStringWas(helper.format(now));
	}

	@Test
	public void testSerialize() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		entity.serialize(now, generator, serializerProvider);
		assertGeneratedStringWas(helper.format(now));
	}

	private void assertGeneratedStringWas(final String toCheck) throws IOException {
		verify(generator, times(1)).writeString(toCheck);
	}

}
