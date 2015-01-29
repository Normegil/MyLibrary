package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.user.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
public class RightDatabaseDAO extends DatabaseDAO<Right> {

	public RightDatabaseDAO() {
	}

	public RightDatabaseDAO(@NotNull final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Right> getEntityClass() {
		return Right.class;
	}

	@Override
	protected List<Order> getOrderByParameters(final CriteriaBuilder builder, final Root<Right> root) {
		return Arrays.asList(
				builder.asc(root.get("resource")),
				builder.asc(root.get("method"))
		);
	}

	public Optional<Right> get(@NotNull final Group group, @NotNull final Resource resource, @NotNull final RESTMethod method) {
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

	public Optional<Right> get(@NotNull final User user, @NotNull final SpecificResource resource, @NotNull final RESTMethod method) {
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

	protected CriteriaQuery<Right> getQuery(final Object owner, final Resource resource, final RESTMethod method) {
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
