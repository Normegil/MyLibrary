package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

@RunWith(MockitoJUnitRunner.class)
public class UTKeyDatabaseDAOSafety {

	private static final ClassWrapper<KeyDatabaseDAO> CLASS = new ClassWrapper<>(KeyDatabaseDAO.class);
	private KeyDatabaseDAO entity = new KeyDatabaseDAO();

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Group> root;

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullEntityManager() throws Exception {
		Validator.validate(CLASS.getConstructor(EntityManager.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetOrderByParameters_NullBuilder() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getOrderByParameters", CriteriaBuilder.class, Root.class), null, root);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetOrderByParameters_NullRoot() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getOrderByParameters", CriteriaBuilder.class, Root.class), criteriaBuilder, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByName_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getByName", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByName_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getByName", String.class), "");
	}
}
