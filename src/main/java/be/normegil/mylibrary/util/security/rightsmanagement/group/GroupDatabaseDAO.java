package be.normegil.mylibrary.util.security.rightsmanagement.group;

import be.normegil.mylibrary.util.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class GroupDatabaseDAO extends DatabaseDAO<Group> {

	public GroupDatabaseDAO() {
	}

	public GroupDatabaseDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Group> getEntityClass() {
		return Group.class;
	}
}
