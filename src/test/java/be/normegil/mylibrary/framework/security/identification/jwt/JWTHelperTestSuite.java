package be.normegil.mylibrary.framework.security.identification.jwt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTJWTHelperSafety.class,
		UTJWTHelper.class
})
public class JWTHelperTestSuite {
}