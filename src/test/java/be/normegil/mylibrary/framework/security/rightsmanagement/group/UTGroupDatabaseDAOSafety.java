package be.normegil.mylibrary.framework.security.rightsmanagement.group;

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
public class UTGroupDatabaseDAOSafety {

	private static final ClassWrapper<GroupDatabaseDAO> CLASS = new ClassWrapper<>(GroupDatabaseDAO.class);
	private GroupDatabaseDAO entity = new GroupDatabaseDAO();

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
}
