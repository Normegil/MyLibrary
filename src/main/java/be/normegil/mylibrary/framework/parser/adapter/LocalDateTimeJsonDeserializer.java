package be.normegil.mylibrary.framework.parser.adapter;

import be.normegil.mylibrary.framework.DateHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

	private DateHelper helper = new DateHelper();

	@Override
	public LocalDateTime deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		return helper.parseLocalDateTime(jp.getText());
	}
}
