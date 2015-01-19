package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Stateless
public class GroupDatabaseDAO extends DatabaseDAO<Group> {

	public GroupDatabaseDAO() {
	}

	public GroupDatabaseDAO(@NotNull final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Group> getEntityClass() {
		return Group.class;
	}

	@Override
	protected List<Order> getOrderByParameters(@NotNull final CriteriaBuilder builder, @NotNull final Root<Group> root) {
		return Arrays.asList(
				builder.asc(root.get("name"))
		);
	}
}
