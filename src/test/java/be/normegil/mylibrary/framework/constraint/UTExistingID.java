package be.normegil.mylibrary.framework.constraint;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UTExistingID {

	@Mock
	private ConstraintValidatorContext context;

	private ExistingIDValidator validator = new ExistingIDValidator();

	@Test
	public void testIsValid_ValidEntity() throws Exception {
		Entity entity = new FakeEntity();
		new EntityHelper().setId(entity, UUID.randomUUID());
		assertTrue(validator.isValid(entity, context));
	}

	@Test
	public void testIsValid_InvalidEntity() throws Exception {
		Entity entity = new FakeEntity();
		assertFalse(validator.isValid(entity, context));
	}

	@Test
	public void testIsValid_NullEntity() throws Exception {
		assertFalse(validator.isValid(null, context));
	}

	@Test
	public void testIsValid_NullContext() throws Exception {
		Entity entity = new FakeEntity();
		new EntityHelper().setId(entity, UUID.randomUUID());
		assertTrue(validator.isValid(entity, null));
	}
}
