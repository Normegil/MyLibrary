package be.normegil.mylibrary.framework.parser.adapter;

import be.normegil.mylibrary.framework.DateHelper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

	private DateHelper helper = new DateHelper();

	@Override
	public void serialize(final LocalDateTime time, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
		if (jgen == null) {
			throw new IllegalArgumentException("JsonGenerator should not be null");
		} else {
			String toWrite;
			if (time != null) {
				toWrite = helper.format(time);
			} else {
				toWrite = "";
			}

			jgen.writeString(toWrite);
		}
	}
}
