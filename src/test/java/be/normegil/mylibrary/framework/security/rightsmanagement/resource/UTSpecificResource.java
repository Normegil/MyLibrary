package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.user.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UTSpecificResource {

	private static final IGenerator<User> userGenerator = GeneratorRepository.get(User.class);

	@Test
	public void testConstructor_RessourceID() throws Exception {
		String resourceID = UUID.randomUUID().toString();
		SpecificResource resource = new SpecificResource(MangaREST.class, resourceID);
		assertEquals(resourceID, resource.getRessourceID());
	}

	@Test
	public void testConstructor_Owner() throws Exception {
		String resourceID = UUID.randomUUID().toString();
		SpecificResource resource = new SpecificResource(MangaREST.class, resourceID);
		assertFalse(resource.getOwner().isPresent());
	}

	@Test
	public void testUserConstructor_Owner() throws Exception {
		User user = userGenerator.getNew(false, false);
		SpecificResource resource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString(), user);
		assertEquals(user, resource.getOwner().get());
	}

	@Test
	public void testToString_WithoutOwner() throws Exception {
		String resourceID = UUID.randomUUID().toString();
		SpecificResource resource = new SpecificResource(MangaREST.class, resourceID);
		String resourceString = resource.toString();
		assertTrue(!StringUtils.isBlank(resourceString) && !resourceString.contains("owner"));
	}

	@Test
	public void testToString_WithOwner() throws Exception {
		String resourceID = UUID.randomUUID().toString();
		SpecificResource resource = new SpecificResource(MangaREST.class, resourceID, userGenerator.getDefault(false, false));
		String resourceString = resource.toString();
		assertTrue(!StringUtils.isBlank(resourceString) && resourceString.contains("owner"));
	}

}
