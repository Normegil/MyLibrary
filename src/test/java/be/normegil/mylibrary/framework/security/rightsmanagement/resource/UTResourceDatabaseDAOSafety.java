package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;

@RunWith(MockitoJUnitRunner.class)
public class UTResourceDatabaseDAOSafety {

	private static final ClassWrapper<ResourceDatabaseDAO> CLASS = new ClassWrapper<>(ResourceDatabaseDAO.class);
	private ResourceDatabaseDAO entity = new ResourceDatabaseDAO();

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private CriteriaQuery criteriaQuery;

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
	public void testGetByClass_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getByClass", Class.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetQuery_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getQuery", Class.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullCriteriaBuilder() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), null, criteriaQuery, MangaREST.class);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullCriteriaQuery() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), criteriaBuilder, null, MangaREST.class);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullClass() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), criteriaBuilder, criteriaQuery, null);
	}

	private Method getGenerateWhereClauseMethod() {
		return CLASS.getMethod("generateWhereClause", CriteriaBuilder.class, CriteriaQuery.class, Class.class);
	}
}
