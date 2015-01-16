package be.normegil.mylibrary.framework.constraint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ExistingIDTestSuite.class,
		NotEmptyValidatorTestSuite.class,
		URIWithIDValidatorTestSuite.class
})
public class ConstraintPackageTestSuite {
}