package be.normegil.mylibrary.user;

import be.normegil.mylibrary.util.constraint.NotEmpty;
import be.normegil.mylibrary.util.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

@Stateless
public class UserDatabaseDAO extends DatabaseDAO<User> {

	private static final String USER_PSEUDO_FIELD_NAME = "pseudo";

	public UserDatabaseDAO() {
	}

	public UserDatabaseDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	public Optional<User> getByPseudo(@NotEmpty String pseudo) {
		CriteriaQuery<User> query = getSelectUserByPseudoQuery(pseudo);
		User user;
		try {
			user = getEntityManager()
					.createQuery(query)
					.getSingleResult();
			return Optional.ofNullable(user);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	private CriteriaQuery<User> getSelectUserByPseudoQuery(final String pseudo) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(getEntityClass());
		generateWhereClause(pseudo, builder, query);
		return query;
	}

	private void generateWhereClause(final String pseudo, final CriteriaBuilder builder, final CriteriaQuery<User> query) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<User> userType = metamodel.entity(getEntityClass());
		Root<User> user = query.from(getEntityClass());
		SingularAttribute<User, String> name = userType.getDeclaredSingularAttribute(USER_PSEUDO_FIELD_NAME, String.class);
		Path<String> namePath = user.get(name);
		Predicate predicate = builder.equal(namePath, pseudo);
		query.where(predicate);
	}
}
