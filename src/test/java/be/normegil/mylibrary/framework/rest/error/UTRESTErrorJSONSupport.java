package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import be.normegil.librarium.tool.test.model.data.AbstractParsingSupportTest;
import be.normegil.librarium.util.parser.Parser;

public class UTRESTErrorJSONSupport extends AbstractParsingSupportTest<RESTError> {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<RESTError> FACTORY = FactoryRepository.get(RESTError.class);

	public UTRESTErrorJSONSupport() {
		super(Parser.DocumentType.JSON, RESTError.class);
	}

	@Override
	protected RESTError initEntity() {
		return FACTORY.getDefault();
	}
}
