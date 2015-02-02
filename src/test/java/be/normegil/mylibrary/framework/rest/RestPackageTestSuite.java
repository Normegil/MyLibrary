package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.rest.error.ErrorPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ErrorPackageTestSuite.class,

		CollectionResourceTestSuite.class,
		HttpStatusTestSuite.class,
		RESTHelperTestSuite.class,
		RESTServicesTestSuite.class
})
public class RestPackageTestSuite {
}