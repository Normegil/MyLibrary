package be.normegil.mylibrary.framework.dao;

import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTDatabaseDAOSafety {

	private final static ClassWrapper<DatabaseDAO> CLASS = new ClassWrapper<>(DatabaseDAO.class);
	public DatabaseDAO entity;

	@Before
	public void setUp() throws Exception {
		entity = new DatabaseDAO<Manga>() {
			@Override
			protected Class<Manga> getEntityClass() {
				return Manga.class;
			}
		};
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGet_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("get", Object.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testPersist_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("persist", Object.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testRemove_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("remove", Object.class), new Object[]{null});
	}
}
