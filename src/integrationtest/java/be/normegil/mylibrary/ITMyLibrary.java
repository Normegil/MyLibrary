package be.normegil.mylibrary;

import be.normegil.mylibrary.framework.FrameworkPackageIntegrationTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		FrameworkPackageIntegrationTestSuite.class
})
public class ITMyLibrary {
}
