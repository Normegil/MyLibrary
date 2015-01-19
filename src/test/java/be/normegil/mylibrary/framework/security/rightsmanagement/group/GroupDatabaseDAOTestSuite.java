package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTGroupDatabaseDAOSafety.class,
		UTGroupDatabaseDAO.class,
		ITGroupDatabaseDAO.class
})
public class GroupDatabaseDAOTestSuite {
}