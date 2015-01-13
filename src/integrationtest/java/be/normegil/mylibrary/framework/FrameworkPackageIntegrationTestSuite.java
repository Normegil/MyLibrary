package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.framework.dao.DAOPackageIntegrationTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DAOPackageIntegrationTestSuite.class
})
public class FrameworkPackageIntegrationTestSuite {
}