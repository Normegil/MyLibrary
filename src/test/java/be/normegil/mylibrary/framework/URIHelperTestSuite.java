package be.normegil.mylibrary.framework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTURIHelperSafety.class,
		UTURIHelper.class
})
public class URIHelperTestSuite {
}