package be.normegil.mylibrary.framework.constraint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UTURIWithIDValidator {

	private static final URI INVALID_URI = URI.create("http://www.example.com/rest");
	private static final URI INVALID_URI_ENDING_WITH_SLASH = URI.create("http://www.example.com/rest/");
	private static final URI VALID_URI = URI.create(INVALID_URI_ENDING_WITH_SLASH + UUID.randomUUID().toString());

	@Mock
	private ConstraintValidatorContext context;

	private URIWithIDValidator validator = new URIWithIDValidator();

	@Test
	public void testInitialize() throws Exception {
		validator.initialize(null);
	}

	@Test
	public void testIsValid_WithoutID() throws Exception {
		assertFalse(validator.isValid(INVALID_URI, context));
	}

	@Test
	public void testIsValid_WithoutID_WithEndingSlash() throws Exception {
		assertFalse(validator.isValid(INVALID_URI_ENDING_WITH_SLASH, context));
	}

	@Test
	public void testIsValid_WithID() throws Exception {
		assertTrue(validator.isValid(VALID_URI, context));
	}

	@Test
	public void testIsValid_NullURI() throws Exception {
		assertFalse(validator.isValid(null, context));
	}

	@Test
	public void testIsValid_NullContext() throws Exception {
		assertTrue(validator.isValid(VALID_URI, null));

	}
}
