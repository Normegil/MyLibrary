package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.Validator;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class UTSpecificResourceSafety {

	private static final ClassWrapper<SpecificResource> CLASS = new ClassWrapper<>(SpecificResource.class);
	private static final IGenerator<User> userGenerator = GeneratorRepository.get(User.class);
	public static final Class<MangaREST> REST_SERVICE_CLASS = MangaREST.class;
	public static final String RESOURCE_ID = UUID.randomUUID().toString();

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullRestService() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class, String.class), new Object[]{null, RESOURCE_ID});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullID() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class, String.class), REST_SERVICE_CLASS, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullRestService() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class, String.class, User.class), new Object[]{null, RESOURCE_ID, userGenerator.getDefault(false, false)});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullID() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class, String.class, User.class), REST_SERVICE_CLASS, null, userGenerator.getDefault(false, false));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testUserConstructor_NullUser() throws Exception {
		Validator.validate(CLASS.getConstructor(Class.class, String.class, User.class), REST_SERVICE_CLASS, RESOURCE_ID, null);
	}
}
