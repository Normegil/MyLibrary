package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.constraint.ValidDateFormat;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.util.Date;

@Stateless
public class DateHelper {
	public String format(@NotNull LocalDate date) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(date.atStartOfDay(), ZoneId.systemDefault());
		return toString(zonedDateTime);
	}


	public LocalDate parseLocalDate(@ValidDateFormat String date) {
		ZonedDateTime zonedDateTime = from(date);
		return zonedDateTime.toLocalDate();
	}

	public String format(@NotNull final LocalDateTime time) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneId.systemDefault());
		return toString(zonedDateTime);
	}

	public LocalDateTime parseLocalDateTime(@ValidDateFormat String date) {
		ZonedDateTime zonedDateTime = from(date);
		return zonedDateTime.toLocalDateTime();
	}

	public LocalDateTime from(final Date date) {
		long time = date.getTime();
		Instant instant = Instant.ofEpochMilli(time);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	public Date toDate(final LocalDateTime time) {
		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = time.atZone(zone);
		Instant instant = zonedDateTime.toInstant();
		return Date.from(instant);
	}

	private String toString(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.format(ApplicationProperties.STANDARD_TIME_FORMAT);
	}

	private ZonedDateTime from(final String date) {
		Instant instant = Instant.parse(date);
		return instant.atZone(ZoneId.systemDefault());
	}
}
