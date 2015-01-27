package be.normegil.mylibrary.framework.security.identification.key;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		KeyTestSuite.class,
		KeyDatabaseDAO.class,
		KeyManagerTestSuite.class
})
public class KeyPackageTestSuite {
}