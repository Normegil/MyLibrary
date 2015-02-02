package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import be.normegil.mylibrary.tools.test.AbstractDataEqualityTest;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class UTSpecificResourceEquality extends AbstractDataEqualityTest<SpecificResource> {

	private static final IGenerator<SpecificResource> GENERATOR = GeneratorRepository.get(SpecificResource.class);
	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);

	@Override
	protected SpecificResource getNewEntity() {
		return GENERATOR.getDefault(false, false);
	}

	@Test
	public void testUnchanged() throws Exception {
		SpecificResource entity = getEntity();
		SpecificResource copy = new SpecificResource(entity.getRestService(), entity.getRessourceID(), entity.getOwner().orElse(null));
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentRestService() throws Exception {
		SpecificResource entity = getEntity();
		SpecificResource copy = new SpecificResource(FakeEntity.RestService.class, entity.getRessourceID(), entity.getOwner().orElse(null));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentResourceID() throws Exception {
		SpecificResource entity = getEntity();
		SpecificResource copy = new SpecificResource(entity.getRestService(), UUID.randomUUID().toString(), entity.getOwner().orElse(null));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentOwner() throws Exception {
		SpecificResource entity = getEntity();
		SpecificResource copy = new SpecificResource(entity.getRestService(), entity.getRessourceID(), USER_GENERATOR.getNew(false, false));
		assertEquals(entity, copy);
	}

	@Test
	public void testHashcode() throws Exception {
		SpecificResource entity = GENERATOR.getNew(false, false);
		assertNotNull(entity.hashCode());
	}
}
