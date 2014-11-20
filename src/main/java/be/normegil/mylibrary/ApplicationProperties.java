package be.normegil.mylibrary;

import org.apache.commons.lang3.builder.ToStringStyle;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(ApplicationProperties.BASE_PATH)
public class ApplicationProperties extends Application {
	protected static final String BASE_PATH = "/rest";

	public static final String PERSISTENCE_UNIT_NAME = "MainPU";
	public static final ToStringStyle TO_STRING_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;

	public class REST {
		public static final int DEFAULT_LIMIT = 25;
		public static final int MAX_LIMIT = 500;
	}
}
