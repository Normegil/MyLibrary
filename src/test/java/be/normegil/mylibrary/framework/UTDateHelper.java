package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.ApplicationProperties;
import org.junit.Test;

import java.time.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UTDateHelper {

	private DateHelper dateHelper = new DateHelper();

	@Test
	public void testFormatLocalDate() throws Exception {
		LocalDate date = LocalDate.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(date.atStartOfDay(), ZoneId.systemDefault());
		String dateString = zonedDateTime.format(ApplicationProperties.STANDARD_TIME_FORMAT);
		assertEquals(dateString, dateHelper.format(date));
	}

	@Test
	public void testParseLocalDate() throws Exception {
		LocalDate date = LocalDate.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(date.atStartOfDay(), ZoneId.systemDefault());
		String dateString = zonedDateTime.format(ApplicationProperties.STANDARD_TIME_FORMAT);
		assertEquals(date, dateHelper.parseLocalDate(dateString));
	}

	@Test
	public void testFormatLocalDateTime() throws Exception {
		LocalDateTime time = LocalDateTime.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneId.systemDefault());
		String dateString = zonedDateTime.format(ApplicationProperties.STANDARD_TIME_FORMAT);
		assertEquals(dateString, dateHelper.format(time));
	}

	@Test
	public void testParseLocalDateTime() throws Exception {
		LocalDateTime time = LocalDateTime.now();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneId.systemDefault());
		String dateString = zonedDateTime.format(ApplicationProperties.STANDARD_TIME_FORMAT);
		assertEquals(time, dateHelper.parseLocalDateTime(dateString));
	}

	@Test
	public void testFromDate() throws Exception {
		Date now = new Date();
		long time = now.getTime();
		Instant instant = Instant.ofEpochMilli(time);
		ZoneId zone = ZoneId.systemDefault();

		assertEquals(LocalDateTime.ofInstant(instant, zone), dateHelper.from(now));
	}

	@Test
	public void testToDate() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = now.atZone(zone);
		Instant instant = zonedDateTime.toInstant();

		assertEquals(Date.from(instant), dateHelper.toDate(now));
	}
}
