package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.framework.constraint.ConstraintPackageTestSuite;
import be.normegil.mylibrary.framework.dao.DAOPackageTestSuite;
import be.normegil.mylibrary.framework.parser.ParserPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ConstraintPackageTestSuite.class,
		DAOPackageTestSuite.class,
		ParserPackageTestSuite.class,

		CoupleTestSuite.class,
		DateHelperTestSuite.class,
		EntityTestSuite.class,
		NumbersHelperTestSuite.class,
		URIHelper.class
})
public class FrameworkPackageTestSuite {
}