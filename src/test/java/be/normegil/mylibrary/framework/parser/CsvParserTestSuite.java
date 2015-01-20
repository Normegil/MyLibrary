package be.normegil.mylibrary.framework.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTCsvParserSafety.class,
		UTCsvParser.class
})
public class CsvParserTestSuite {
}