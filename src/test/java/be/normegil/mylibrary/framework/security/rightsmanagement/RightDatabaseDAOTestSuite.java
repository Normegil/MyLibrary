package be.normegil.mylibrary.framework.security.rightsmanagement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTRightDatabaseDAOSafety.class,
		UTRightDatabaseDAO.class,
		ITRightDatabaseDAO.class
})
public class RightDatabaseDAOTestSuite {
}