package be.normegil.mylibrary.framework.security;

import be.normegil.mylibrary.framework.security.identification.IdentificationPackageTestSuite;
import be.normegil.mylibrary.framework.security.rightsmanagement.RightsManagementPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		IdentificationPackageTestSuite.class,
		RightsManagementPackageTestSuite.class,

		SecurityInterceptorTestSuite.class
})
public class SecurityPackageTestSuite {
}