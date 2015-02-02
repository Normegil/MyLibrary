package be.normegil.mylibrary.framework.rest.error;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		DefaultExceptionMapperTestSuite.class,
		ErrorCodeTestSuite.class,
		ErrorRepositoryTestSuite.class,
		RESTErrorTestSuite.class
})
public class ErrorPackageTestSuite {
}
