package be.normegil.mylibrary.framework.security.identification;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTAuthenticatorSafety.class,
		UTAuthenticator.class
})
public class AuthenticatorTestSuite {
}
