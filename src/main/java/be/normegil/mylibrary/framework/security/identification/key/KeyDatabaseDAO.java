package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
public class KeyDatabaseDAO extends DatabaseDAO<Key> {

	private static final String KEY_NAME_FIELD_NAME = "name";

	protected KeyDatabaseDAO() {
	}

	public KeyDatabaseDAO(@NotNull final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Key> getEntityClass() {
		return Key.class;
	}

	@Override
	protected List<Order> getOrderByParameters(final CriteriaBuilder builder, final Root<Key> root) {
		return Arrays.asList(
				builder.asc(root.get("name"))
		);
	}

	public Optional<Key> getByName(@NotEmpty String name) {
		CriteriaQuery<Key> query = getSelectKeyByNameQuery(name);
		List<Key> resultList = getEntityManager()
				.createQuery(query)
				.getResultList();

		if (resultList.isEmpty()) {
			return Optional.empty();
		} else {
			// Pseudo is unique
			return Optional.of(resultList.get(0));
		}
	}

	private CriteriaQuery<Key> getSelectKeyByNameQuery(final String keyName) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Key> query = builder.createQuery(getEntityClass());
		generateWhereClause(keyName, builder, query);
		return query;
	}

	private void generateWhereClause(final String name, final CriteriaBuilder builder, final CriteriaQuery<Key> query) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<Key> keyType = metamodel.entity(getEntityClass());
		Root<Key> key = query.from(getEntityClass());
		Path<String> path = key.get(keyType.getSingularAttribute(KEY_NAME_FIELD_NAME, String.class));
		Predicate predicate = builder.equal(path, name);
		query.where(predicate);
	}
}
