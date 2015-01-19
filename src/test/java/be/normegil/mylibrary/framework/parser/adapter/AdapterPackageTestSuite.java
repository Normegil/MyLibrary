package be.normegil.mylibrary.framework.parser.adapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		LocalDateTimeJsonDeserializerTestSuite.class,
		LocalDateTimeJsonSerializerTestSuite.class
})
public class AdapterPackageTestSuite {
}