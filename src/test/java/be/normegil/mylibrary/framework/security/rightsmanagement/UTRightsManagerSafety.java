package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.Validator;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTRightsManagerSafety {

	private static final ClassWrapper<RightsManager> CLASS = new ClassWrapper<>(RightsManager.class);
	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	private static final java.lang.reflect.Method CAN_ACCESS_USER = CLASS.getMethod("canAccess", User.class, Resource.class, RESTMethod.class);
	private static final java.lang.reflect.Method CAN_ACCESS_GROUP = CLASS.getMethod("canAccess", Group.class, Resource.class, RESTMethod.class);
	private static final java.lang.reflect.Method GRANT_ACCESS_GROUP = CLASS.getMethod("grantAccess", Group.class, Resource.class, RESTMethod.class);
	private static final java.lang.reflect.Method DENY_ACCESS_GROUP = CLASS.getMethod("denyAccess", Group.class, Resource.class, RESTMethod.class);
	private static final Resource DEFAULT_RESOURCE = new Resource(MangaREST.class);
	private static final RESTMethod REST_METHOD = RESTMethod.GET;
	private RightsManager entity = new RightsManager();

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessUser_NullUser() throws Exception {
		Validator.validate(entity, CAN_ACCESS_USER, null, DEFAULT_RESOURCE, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessUser_NullResource() throws Exception {
		Validator.validate(entity, CAN_ACCESS_USER, USER_GENERATOR.getDefault(false, false), null, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessUser_NullMethod() throws Exception {
		Validator.validate(entity, CAN_ACCESS_USER, USER_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessGroup_NullGroup() throws Exception {
		Validator.validate(entity, CAN_ACCESS_GROUP, null, DEFAULT_RESOURCE, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessGroup_NullResource() throws Exception {
		Validator.validate(entity, CAN_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), null, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testCanAccessGroup_NullMethod() throws Exception {
		Validator.validate(entity, CAN_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessUser_NullUser() throws Exception {
		Validator.validate(entity, DENY_ACCESS_GROUP, null, DEFAULT_RESOURCE, REST_METHOD);
	}


	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessUser_NullResource() throws Exception {
		Validator.validate(entity, DENY_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), null, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessUser_NullMethod() throws Exception {
		Validator.validate(entity, DENY_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessGroup_NullGroup() throws Exception {
		Validator.validate(entity, GRANT_ACCESS_GROUP, null, DEFAULT_RESOURCE, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessGroup_NullResource() throws Exception {
		Validator.validate(entity, GRANT_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), null, REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGrantAccessGroup_NullMethod() throws Exception {
		Validator.validate(entity, GRANT_ACCESS_GROUP, GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}
}
