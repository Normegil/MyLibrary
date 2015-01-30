package be.normegil.mylibrary.framework.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTSecurityInterceptorSafety.class,
		UTSecurityInterceptor.class
})
public class SecurityInterceptorTestSuite {
}