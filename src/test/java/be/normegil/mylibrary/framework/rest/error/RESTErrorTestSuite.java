package be.normegil.mylibrary.framework.rest.error;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTRESTErrorSafety.class,
		UTRESTError.class,
		UTRESTErrorEquality.class,
		UTRESTErrorBuilderSafety.class,
		UTRESTErrorBuilder.class
})
public class RESTErrorTestSuite {
}