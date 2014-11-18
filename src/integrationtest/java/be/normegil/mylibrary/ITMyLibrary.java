package be.normegil.mylibrary;

import be.normegil.mylibrary.dao.DAOPackageIntegrationTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DAOPackageIntegrationTestSuite.class
})
public class ITMyLibrary {
}
