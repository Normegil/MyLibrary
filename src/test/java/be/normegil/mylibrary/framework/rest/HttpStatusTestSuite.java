package be.normegil.mylibrary.framework.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTHttpStatus.class,
		UTCategory.class
})
public class HttpStatusTestSuite {
}