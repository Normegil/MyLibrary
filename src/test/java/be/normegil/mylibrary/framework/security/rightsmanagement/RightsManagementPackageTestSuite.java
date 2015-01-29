package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.security.rightsmanagement.group.GroupPackageTestSuite;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourcePackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		GroupPackageTestSuite.class,
		ResourcePackageTestSuite.class,

		RightTestSuite.class,
		RightDatabaseDAOTestSuite.class
})
public class RightsManagementPackageTestSuite {
}