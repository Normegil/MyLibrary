package be.normegil.mylibrary;

import org.apache.commons.lang3.builder.ToStringStyle;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

@ApplicationPath(ApplicationProperties.BASE_PATH)
public class ApplicationProperties extends Application {
	protected static final String BASE_PATH = "/rest";

	public static final String PERSISTENCE_UNIT_NAME = "MainPU";
	public static final DateTimeFormatter STANDARD_TIME_FORMAT = DateTimeFormatter.ISO_INSTANT;
	public static final ToStringStyle TO_STRING_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;

	public class REST {
		public static final int DEFAULT_LIMIT = 25;
		public static final int MAX_LIMIT = 500;
	}

	public static class Security {
		public static class JSonWebToken {
			public static final TemporalAmount TOKEN_VALIDITY_PERIOD = Duration.of(2, ChronoUnit.DAYS);
		}
	}
}
