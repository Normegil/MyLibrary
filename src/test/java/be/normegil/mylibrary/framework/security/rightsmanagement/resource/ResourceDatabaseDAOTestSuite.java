package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTResourceDatabaseDAOSafety.class,
		UTResourceDatabaseDAO.class,
		ITResourceDatabaseDAO.class
})
public class ResourceDatabaseDAOTestSuite {
}