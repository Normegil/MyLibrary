package be.normegil.mylibrary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(ApplicationProperties.BASE_PATH)
public class ApplicationProperties extends Application {
	protected static final String BASE_PATH = "/rest";

	public static final String PERSISTENCE_UNIT_NAME = "MainPU";

	public class REST {
		public static final int DEFAULT_LIMIT = 25;
		public static final int MAX_LIMIT = 500;
	}
}
