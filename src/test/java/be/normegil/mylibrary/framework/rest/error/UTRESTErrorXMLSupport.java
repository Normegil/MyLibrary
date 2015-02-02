package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.test.model.data.AbstractParsingSupportTest;
import be.normegil.librarium.util.parser.Parser;

public class UTRESTErrorXMLSupport extends AbstractParsingSupportTest<RESTError> {

	private static final DataFactory<RESTError> FACTORY = new RESTErrorTestSuite();

	public UTRESTErrorXMLSupport() {
		super(Parser.DocumentType.XML, RESTError.class);
	}

	@Override
	protected RESTError initEntity() {
		return FACTORY.getDefault();
	}
}
