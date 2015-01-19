package be.normegil.mylibrary.framework.parser.adapter;

import be.normegil.mylibrary.framework.DateHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

	private DateHelper helper = new DateHelper();

	@Override
	public LocalDateTime deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		if (jp == null) {
			throw new IllegalArgumentException("Parser cannot be null");
		} else {
			String text = jp.getText();
			if (StringUtils.isBlank(text)) {
				return null;
			} else {
				return helper.parseLocalDateTime(jp.getText());
			}
		}
	}
}
