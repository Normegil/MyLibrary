package be.normegil.mylibrary.framework.exception;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTWebApplicationExceptionSafety.class,
		UTWebApplicationException.class
})
public class WebApplicationExceptionTestSuite {
}