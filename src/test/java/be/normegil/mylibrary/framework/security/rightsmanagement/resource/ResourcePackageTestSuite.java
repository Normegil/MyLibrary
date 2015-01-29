package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ResourceTestSuite.class,
		SpecificResourceTestSuite.class,
		ResourceDatabaseDAOTestSuite.class,
		SpecificResourceDatabaseDAOTestSuite.class
})
public class ResourcePackageTestSuite {
}