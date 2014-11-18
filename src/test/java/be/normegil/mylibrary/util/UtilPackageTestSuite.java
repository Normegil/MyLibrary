package be.normegil.mylibrary.util;

import be.normegil.mylibrary.util.constraint.ConstraintPackageTestSuite;
import be.normegil.mylibrary.util.dao.DAOPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ConstraintPackageTestSuite.class,
		DAOPackageTestSuite.class
})
public class UtilPackageTestSuite {
}