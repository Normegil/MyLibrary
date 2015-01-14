package be.normegil.mylibrary.framework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTEntity.class,
		UTEntityDigestSafety.class,
		UTEntityDigest.class
})
public class EntityTestSuite {
}