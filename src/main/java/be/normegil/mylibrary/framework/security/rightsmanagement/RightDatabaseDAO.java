package be.normegil.mylibrary.util.security.rightsmanagement;

import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.util.dao.DatabaseDAO;
import be.normegil.mylibrary.util.rest.HttpMethod;
import be.normegil.mylibrary.util.rest.RESTMethod;
import be.normegil.mylibrary.util.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.util.security.rightsmanagement.ressource.Resource;
import be.normegil.mylibrary.util.security.rightsmanagement.ressource.SpecificResource;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

@Stateless
public class RightDatabaseDAO extends DatabaseDAO<Right> {

	public RightDatabaseDAO() {
	}

	public RightDatabaseDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Right> getEntityClass() {
		return Right.class;
	}

	public Optional<Right> get(final Group group, final Resource resource, final RESTMethod method) {
		CriteriaQuery<Right> query = getQuery(group, resource, method);
		try {
			Right right = getEntityManager()
					.createQuery(query)
					.getSingleResult();
			return Optional.ofNullable(right);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	public Optional<Right> get(final User user, final SpecificResource resource, final RESTMethod method) {
		CriteriaQuery<Right> query = getQuery(user, resource, method);
		Right right;
		try {
			right = getEntityManager()
					.createQuery(query)
					.getSingleResult();
			return Optional.ofNullable(right);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	private CriteriaQuery<Right> getQuery(final Object owner, final Resource resource, final RESTMethod method) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Right> query = builder.createQuery(getEntityClass());

		generateWhereClause(builder, query, owner, resource, method);
		return query;
	}

	private Predicate getOwnerPredicate(final Object owner, final CriteriaBuilder builder, final Root<Right> root) {
		Predicate predicate;
		if (owner instanceof User) {
			predicate = getPredicate(builder, root, (User) owner);
		} else if (owner instanceof Group) {
			predicate = getPredicate(builder, root, (Group) owner);
		} else {
			throw new IllegalArgumentException("OwnerType not managed");
		}
		return predicate;
	}

	private void generateWhereClause(final CriteriaBuilder builder, final CriteriaQuery<Right> query, final Object owner, final Resource resource, final RESTMethod method) {
		EntityType<Right> userType = getRightEntityType();
		Root<Right> right = getRoot(query);

		SingularAttribute<Right, Resource> resourceAttribute = userType.getDeclaredSingularAttribute("resource", Resource.class);
		Path<Resource> resourcePath = right.get(resourceAttribute);
		Predicate resourcePredicate = builder.equal(resourcePath, resource);

		SingularAttribute<Right, RESTMethod> methodAttribute = userType.getDeclaredSingularAttribute("method", RESTMethod.class);
		Path<RESTMethod> methodPath = right.get(methodAttribute);
		Predicate methodPredicate = builder.equal(methodPath, method);

		query.select(right);
		query.where(getOwnerPredicate(owner, builder, right), resourcePredicate, methodPredicate);
	}

	private Predicate getPredicate(final CriteriaBuilder builder, final Root<Right> root, final Group group) {
		EntityType<Right> userType = getRightEntityType();
		SingularAttribute<Right, Group> groupAttribute = userType.getDeclaredSingularAttribute("group", Group.class);
		Path<Group> groupPath = root.get(groupAttribute);
		return builder.equal(groupPath, group);
	}

	private Predicate getPredicate(final CriteriaBuilder builder, final Root<Right> root, final User user) {
		EntityType<Right> userType = getRightEntityType();
		SingularAttribute<Right, User> attribute = userType.getDeclaredSingularAttribute("user", User.class);
		Path<User> path = root.get(attribute);
		return builder.equal(path, user);
	}

	private Root<Right> getRoot(final CriteriaQuery<Right> query) {
		return query.from(getEntityClass());
	}

	private EntityType<Right> getRightEntityType() {
		Metamodel metamodel = getEntityManager().getMetamodel();
		return metamodel.entity(getEntityClass());
	}
}
