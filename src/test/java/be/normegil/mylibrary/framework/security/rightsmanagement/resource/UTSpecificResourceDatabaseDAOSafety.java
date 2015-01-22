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
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UTSpecificResourceDatabaseDAOSafety {

	private static final ClassWrapper<SpecificResourceDatabaseDAO> CLASS = new ClassWrapper<>(SpecificResourceDatabaseDAO.class);
	public static final String EMPTY_STRING = "";
	private SpecificResourceDatabaseDAO entity = new SpecificResourceDatabaseDAO();

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
	public void testGetByClassAndID_NullID() throws Exception {
		Validator.validate(entity, CLASS.getMethod("get", Class.class, String.class), MangaREST.class, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByClassAndID_EmptyID() throws Exception {
		Validator.validate(entity, CLASS.getMethod("get", Class.class, String.class), MangaREST.class, EMPTY_STRING);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetByClassAndID_NullClass() throws Exception {
		Validator.validate(entity, CLASS.getMethod("get", Class.class, String.class), null, UUID.randomUUID().toString());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetQuery_NullID() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getQuery", Class.class, String.class), MangaREST.class, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetQuery_EmptyID() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getQuery", Class.class, String.class), MangaREST.class, EMPTY_STRING);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetQuery_NullClass() throws Exception {
		Validator.validate(entity, CLASS.getMethod("getQuery", Class.class, String.class), null, UUID.randomUUID().toString());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullCriteriaBuilder() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), null, criteriaQuery, MangaREST.class, UUID.randomUUID().toString());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullCriteriaQuery() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), criteriaBuilder, null, MangaREST.class, UUID.randomUUID().toString());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullClass() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), criteriaBuilder, criteriaQuery, null, UUID.randomUUID().toString());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateWhereClause_NullID() throws Exception {
		Validator.validate(entity, getGenerateWhereClauseMethod(), criteriaBuilder, criteriaQuery, MangaREST.class, null);
	}

	private Method getGenerateWhereClauseMethod() {
		return CLASS.getMethod("generateWhereClause", CriteriaBuilder.class, CriteriaQuery.class, Class.class, String.class);
	}


}
