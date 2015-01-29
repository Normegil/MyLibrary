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
import java.lang.reflect.Constructor;

public class UTRightSafety {

	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	private static final ClassWrapper<Right> CLASS = new ClassWrapper<>(Right.class);
	private static final Constructor<Right> USER_CONSTRUCTOR = CLASS.getConstructor(User.class, Resource.class, RESTMethod.class);
	private static final Constructor<Right> GROUP_CONSTRUCTOR = CLASS.getConstructor(Group.class, Resource.class, RESTMethod.class);
	private static final RESTMethod DEFAULT_REST_METHOD = RESTMethod.GET;
	private static final Resource DEFAULT_RESOURCE = new Resource(MangaREST.class);
	private Right entity;

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullUser() throws Exception {
		Validator.validate(USER_CONSTRUCTOR, new Object[]{null, DEFAULT_RESOURCE, DEFAULT_REST_METHOD});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullResource() throws Exception {
		Validator.validate(USER_CONSTRUCTOR, USER_GENERATOR.getDefault(false, false), null, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullRESTMethod() throws Exception {
		Validator.validate(USER_CONSTRUCTOR, USER_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGroupConstructor_NullGroup() throws Exception {
		Validator.validate(GROUP_CONSTRUCTOR, new Object[]{null, DEFAULT_RESOURCE, DEFAULT_REST_METHOD});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGroupConstructor_NullResource() throws Exception {
		Validator.validate(GROUP_CONSTRUCTOR, GROUP_GENERATOR.getDefault(false, false), null, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGroupConstructor_NullRESTMethod() throws Exception {
		Validator.validate(GROUP_CONSTRUCTOR, GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}
}
