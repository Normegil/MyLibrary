package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.libraries.URL;
import be.normegil.librarium.model.rest.HttpStatus;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTRESTErrorSafety.class,
		UTRESTError.class,
		UTRESTErrorEquality.class,
		UTRESTErrorBuilderSafety.class,
		UTRESTErrorBuilder.class,
		UTRESTErrorJSONSupport.class,
		UTRESTErrorXMLSupport.class,
		UTRESTErrorCSVSupport.class
})
public class RESTErrorTestSuite implements DataFactory<RESTError> {
}