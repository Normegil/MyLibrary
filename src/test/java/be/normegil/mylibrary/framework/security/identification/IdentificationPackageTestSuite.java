package be.normegil.mylibrary.framework.security.identification;

import be.normegil.mylibrary.framework.security.identification.jwt.JWTPackageTestSuite;
import be.normegil.mylibrary.framework.security.identification.key.KeyPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		JWTPackageTestSuite.class,
		KeyPackageTestSuite.class
})
public class IdentificationPackageTestSuite {
}