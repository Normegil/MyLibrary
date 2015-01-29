package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UTRight {

	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	private static final RESTMethod DEFAULT_REST_METHOD = RESTMethod.GET;
	private static final Resource DEFAULT_RESOURCE = new Resource(MangaREST.class);

	@Test
	public void testGetUser_UserConstructor() throws Exception {
		User user = USER_GENERATOR.getNew(false, false);
		Right right = new Right(user, new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), DEFAULT_REST_METHOD);
		assertEquals(user, right.getUser().get());
	}

	@Test
	public void testGetUser_GroupConstructor() throws Exception {
		Right right = new Right(GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, DEFAULT_REST_METHOD);
		assertFalse(right.getUser().isPresent());
	}

	@Test
	public void testGetGroup_GroupConstructor() throws Exception {
		Group group = GROUP_GENERATOR.getNew(false, false);
		Right right = new Right(group, DEFAULT_RESOURCE, DEFAULT_REST_METHOD);
		assertEquals(group, right.getGroup().get());
	}

	@Test
	public void testGetGroup_UserConstructor() throws Exception {
		Right right = new Right(USER_GENERATOR.getDefault(false, false), new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), DEFAULT_REST_METHOD);
		assertFalse(right.getGroup().isPresent());
	}

	@Test
	public void testGetResource() throws Exception {
		SpecificResource resource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString());
		Right right = new Right(USER_GENERATOR.getDefault(false, false), resource, DEFAULT_REST_METHOD);
		assertEquals(resource, right.getResource());
	}

	@Test
	public void testGetMethod() throws Exception {
		Right right = new Right(USER_GENERATOR.getDefault(false, false), new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), DEFAULT_REST_METHOD);
		assertEquals(DEFAULT_REST_METHOD, right.getMethod());
	}
}
