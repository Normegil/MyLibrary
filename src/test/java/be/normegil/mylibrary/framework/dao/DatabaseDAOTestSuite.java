package be.normegil.mylibrary.framework.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTDatabaseDAOSafety.class,
		ITDatabaseDAO.class
})
public class DatabaseDAOTestSuite {
}