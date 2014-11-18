package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.util.dao.DatabaseDAO;

import javax.persistence.EntityManager;

public class DAOHelper {

	public void setEntityManager(DatabaseDAO dao, EntityManager entityManager) {
		ClassWrapper<DatabaseDAO> aClass = new ClassWrapper<>(DatabaseDAO.class);
		FieldWrapper entityManagerField = aClass.getField("entityManager");
		entityManagerField.set(dao, entityManager);
	}
}
