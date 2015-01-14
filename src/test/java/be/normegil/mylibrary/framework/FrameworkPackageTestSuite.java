package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.framework.constraint.ConstraintPackageTestSuite;
import be.normegil.mylibrary.framework.dao.DAOPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ConstraintPackageTestSuite.class,
		DAOPackageTestSuite.class,

		CoupleTestSuite.class,
		DateHelperTestSuite.class,
		EntityTestSuite.class
})
public class FrameworkPackageTestSuite {
}