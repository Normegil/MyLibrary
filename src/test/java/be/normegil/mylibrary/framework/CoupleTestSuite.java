package be.normegil.mylibrary.framework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTCoupleSafety.class,
		UTCouple.class
})
public class CoupleTestSuite {
}