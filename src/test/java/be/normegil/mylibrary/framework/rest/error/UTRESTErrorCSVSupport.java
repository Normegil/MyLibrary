package be.normegil.librarium.model.rest.exception;

import be.normegil.librarium.WarningTypes;
import be.normegil.librarium.model.rest.HttpStatus;
import be.normegil.librarium.tool.DataFactory;
import be.normegil.librarium.tool.FactoryRepository;
import be.normegil.librarium.tool.test.model.data.AbstractCSVSupportTest;
import be.normegil.librarium.util.exception.ReadOnlyRuntimeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UTRESTErrorCSVSupport extends AbstractCSVSupportTest<RESTError> {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final DataFactory<RESTError> FACTORY = FactoryRepository.get(RESTError.class);

	public UTRESTErrorCSVSupport() {
		super(RESTError.class);
	}

	@Override
	protected List<RESTError> initEntity() {
		List<RESTError> errors = new ArrayList<>();
		RESTError error = FACTORY.getDefault();
		RESTError error1 = RESTError.builder()
				.from(error)
				.setCode(error.getCode() + 1)
				.setDeveloperMessage(error.getDeveloperMessage() + 1)
				.setHttpStatus(HttpStatus.ACCEPTED)
				.setMessage(error.getMessage() + 1)
				.setMoreInfoURL(error.getMoreInfoURL().addToPath("1"))
				.build();

		errors.add(error);
		errors.add(error1);
		return errors;
	}

	@Override
	protected void testEquality(final List<RESTError> expected, final List<RESTError> toTest) {
		assertEquals(expected.size(), toTest.size());
		for (int i = 0; i < expected.size(); i++) {
			RESTError expectedError = expected.get(i);
			RESTError toTestError = toTest.get(i);
			assertEquals(expectedError.getCode(), toTestError.getCode());
		}
	}

	@Override
	@Test(expected = ReadOnlyRuntimeException.class)
	public void testMarshaller() throws Exception {
		super.testMarshaller();
	}
}
