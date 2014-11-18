package be.normegil.mylibrary.util;

import be.normegil.mylibrary.util.dao.DAOPackageIntegrationTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DAOPackageIntegrationTestSuite.class
})
public class UtilPackageIntegrationTestSuite {
}