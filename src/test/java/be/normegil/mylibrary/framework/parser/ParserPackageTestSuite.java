package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.framework.parser.adapter.AdapterPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		AdapterPackageTestSuite.class,

		CsvParserTestSuite.class
})
public class ParserPackageTestSuite {
}