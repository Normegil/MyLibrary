package be.normegil.mylibrary.framework.parser.adapter;

import be.normegil.mylibrary.framework.DateHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UTLocalDateTimeJsonDeserializer {

	public static final String EMPTY_STRING = "";
	public static final String INVALID_DATE = "2015:02:25";
	private DateHelper helper = new DateHelper();
	private LocalDateTimeJsonDeserializer entity = new LocalDateTimeJsonDeserializer();

	@Mock
	private JsonParser parser;

	@Mock
	private DeserializationContext context;

	@Test(expected = IllegalArgumentException.class)
	public void testDeserialize_NullParser() throws Exception {
		entity.deserialize(null, context);
	}

	@Test
	public void testDeserialize_EmptyString() throws Exception {
		setupInput(EMPTY_STRING);
		LocalDateTime deserialized = entity.deserialize(parser, context);
		assertNull(deserialized);
	}

	@Test(expected = DateTimeParseException.class)
	public void testDeserialize_InvalidFormat() throws Exception {
		setupInput(INVALID_DATE);
		entity.deserialize(parser, context);
	}

	@Test
	public void testDeserialize_NullContext() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		String validDate = helper.format(now);
		setupInput(validDate);

		LocalDateTime deserialized = entity.deserialize(parser, null);
		assertEquals(now, deserialized);
	}

	@Test
	public void testDeserialize_ValidFormat() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		String validDate = helper.format(now);
		setupInput(validDate);

		LocalDateTime deserialized = entity.deserialize(parser, context);
		assertEquals(now, deserialized);
	}

	private void setupInput(final String input) throws IOException {
		when(parser.getText()).thenReturn(input);
	}
}
