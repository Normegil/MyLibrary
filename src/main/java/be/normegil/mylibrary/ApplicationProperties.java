package be.normegil.mylibrary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(ApplicationProperties.BASE_PATH)
public class ApplicationProperties extends Application {
	protected static final String BASE_PATH = "/rest";
}
