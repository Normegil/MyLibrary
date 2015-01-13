package be.normegil.mylibrary.util.security.identification.key;

import be.normegil.mylibrary.util.constraint.NotEmpty;
import be.normegil.mylibrary.util.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Optional;

@Stateless
public class KeyDatabaseDAO extends DatabaseDAO<Key> {

	private static final String KEY_NAME_FIELD_NAME = "name";

	@Override
	protected Class<Key> getEntityClass() {
		return Key.class;
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
