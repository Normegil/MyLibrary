package be.normegil.mylibrary;

import be.normegil.mylibrary.dao.DAOPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DAOPackageTestSuite.class
})
public class TestMyLibrary {
}
