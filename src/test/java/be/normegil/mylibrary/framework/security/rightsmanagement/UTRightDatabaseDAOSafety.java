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

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;

public class UTRightDatabaseDAOSafety {

	private static final ClassWrapper<RightDatabaseDAO> CLASS = new ClassWrapper<>(RightDatabaseDAO.class);
	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	private static final RESTMethod DEFAULT_REST_METHOD = RESTMethod.GET;
	private static final Resource DEFAULT_RESOURCE = new Resource(MangaREST.class);
	private RightDatabaseDAO entity = new RightDatabaseDAO();
	private Method GET_BY_USER_METHOD = CLASS.getMethod("get", User.class, Resource.class, RESTMethod.class);
	private Method GET_BY_GROUP_METHOD = CLASS.getMethod("get", Group.class, Resource.class, RESTMethod.class);

	@Test(expected = ConstraintViolationException.class)
	public void testEntityManagerConstructor_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(EntityManager.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByUser_NullUser() throws Exception {
		Validator.validate(entity, GET_BY_USER_METHOD, null, DEFAULT_RESOURCE, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByUser_NullResource() throws Exception {
		Validator.validate(entity, GET_BY_USER_METHOD, USER_GENERATOR.getDefault(false, false), null, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByUser_NullMethod() throws Exception {
		Validator.validate(entity, GET_BY_USER_METHOD, USER_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByGroup_NullUser() throws Exception {
		Validator.validate(entity, GET_BY_GROUP_METHOD, null, DEFAULT_RESOURCE, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByGroup_NullResource() throws Exception {
		Validator.validate(entity, GET_BY_GROUP_METHOD, GROUP_GENERATOR.getDefault(false, false), null, DEFAULT_REST_METHOD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByGroup_NullMethod() throws Exception {
		Validator.validate(entity, GET_BY_GROUP_METHOD, GROUP_GENERATOR.getDefault(false, false), DEFAULT_RESOURCE, null);
	}
}
