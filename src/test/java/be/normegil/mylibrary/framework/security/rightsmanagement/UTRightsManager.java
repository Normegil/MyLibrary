package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.ResourceMemoryDAO;
import be.normegil.mylibrary.tools.dao.RightMemoryDAO;
import be.normegil.mylibrary.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UTRightsManager {

	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);

	private RightDAO rightDAO;
	private ResourceDAO resourceDAO;
	private RightsManager rightsManager;

	@Before
	public void setUp() throws Exception {
		rightDAO = new RightMemoryDAO();
		resourceDAO = new ResourceMemoryDAO();
		rightsManager = new RightsManager(rightDAO, resourceDAO);
	}

	@After
	public void tearDown() throws Exception {
		rightsManager = null;
		resourceDAO = null;
		rightDAO = null;
	}

	@Test
	public void testGrantAccess_DAO() throws Exception {
		Group group = GROUP_GENERATOR.getNew(true, false);
		Right right = new Right(group, new Resource(MangaREST.class), RESTMethod.GET);
		rightsManager.grantAccess(right.getGroup().get(), right.getResource(), right.getMethod());
		Optional<Right> rightOptional = rightDAO.get(right.getGroup().get(), right.getResource(), right.getMethod());
		assertTrue(rightOptional.isPresent());
	}

	@Test
	public void testGrantAccess_RightsManager() throws Exception {
		Group group = GROUP_GENERATOR.getNew(true, false);
		Right right = new Right(group, new Resource(MangaREST.class), RESTMethod.GET);
		rightsManager.grantAccess(right.getGroup().get(), right.getResource(), right.getMethod());
		assertTrue(rightsManager.canAccess(right.getGroup().get(), right.getResource(), right.getMethod()));
	}

	@Test
	public void testDenyAccess_DAO() throws Exception {
		Group group = GROUP_GENERATOR.getNew(true, false);
		Right right = new Right(group, new Resource(MangaREST.class), RESTMethod.GET);
		rightsManager.grantAccess(right.getGroup().get(), right.getResource(), right.getMethod());
		rightsManager.denyAccess(right.getGroup().get(), right.getResource(), right.getMethod());
		Optional<Right> rightOptional = rightDAO.get(right.getGroup().get(), right.getResource(), right.getMethod());
		assertFalse(rightOptional.isPresent());
	}

	@Test
	public void testDenyAccess_RightsManager() throws Exception {
		Group group = GROUP_GENERATOR.getNew(true, false);
		Right right = new Right(group, new Resource(MangaREST.class), RESTMethod.GET);
		assertFalse(rightsManager.canAccess(right.getGroup().get(), right.getResource(), right.getMethod()));
	}

	@Test
	public void testCanAccessByGroup() throws Exception {
		Group group = GROUP_GENERATOR.getNew(true, false);
		Right right = new Right(group, new Resource(MangaREST.class), RESTMethod.GET);
		rightDAO.persist(right);
		assertTrue(rightsManager.canAccess(right.getGroup().get(), right.getResource(), right.getMethod()));
	}

	@Test
	public void testCanAccessByUser_Owner() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		SpecificResource specificResource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString(), user);
		resourceDAO.persist(new Resource(specificResource.getRestService()));
		assertTrue(rightsManager.canAccess(user, specificResource, RESTMethod.DELETE));
	}

	@Test
	public void testCanAccessByUser_ByGroup() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		User otherUser = USER_GENERATOR.getNew(true, false);

		Group group = user.getGroups().iterator().next();
		SpecificResource specificResource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString(), otherUser);

		rightsManager.grantAccess(group, new Resource(specificResource.getRestService()), RESTMethod.PUT);
		resourceDAO.persist(new Resource(specificResource.getRestService()));
		assertTrue(rightsManager.canAccess(user, specificResource, RESTMethod.PUT));
	}

	@Test
	public void testCanAccessByUser_NotOwner_NoGroupWithAccess() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		User otherUser = USER_GENERATOR.getNew(true, false);
		SpecificResource specificResource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString(), otherUser);
		resourceDAO.persist(new Resource(specificResource.getRestService()));
		assertFalse(rightsManager.canAccess(user, specificResource, RESTMethod.PUT));
	}

	@Test
	public void testCanAccessByUser_NotOwner_NoGroup() throws Exception {
		User user = USER_GENERATOR.getNew(false, false);
		User otherUser = USER_GENERATOR.getNew(true, false);
		SpecificResource specificResource = new SpecificResource(MangaREST.class, UUID.randomUUID().toString(), otherUser);
		resourceDAO.persist(new Resource(specificResource.getRestService()));
		assertFalse(rightsManager.canAccess(user, specificResource, RESTMethod.PUT));
	}

	@Test(expected = IllegalStateException.class)
	public void testCanAccessByUser_MainResourceNotExisting() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		rightsManager.canAccess(user, new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), RESTMethod.GET);
	}
}
