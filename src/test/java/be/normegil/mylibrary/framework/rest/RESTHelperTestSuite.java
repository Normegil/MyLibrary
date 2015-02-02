package be.normegil.mylibrary.framework.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTRESTHelperSafety.class,
		UTRESTHelper.class
})
public class RESTHelperTestSuite {
}