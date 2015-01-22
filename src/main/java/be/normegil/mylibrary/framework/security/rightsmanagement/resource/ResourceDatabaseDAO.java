package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;
import be.normegil.mylibrary.framework.rest.RESTService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
public class ResourceDatabaseDAO extends DatabaseDAO<Resource> {
	@Override
	protected Class<Resource> getEntityClass() {
		return Resource.class;
	}

	@Override
	protected List<Order> getOrderByParameters(final CriteriaBuilder builder, final Root<Resource> root) {
		return Arrays.asList(
				builder.asc(root.get("restService"))
		);
	}

	public ResourceDatabaseDAO() {
	}

	public ResourceDatabaseDAO(@NotNull final EntityManager entityManager) {
		super(entityManager);
	}

	public Optional<Resource> getByClass(@NotNull final Class<? extends RESTService> restServiceClass) {
		Resource resource = getEntityManager()
				.createQuery(getQuery(restServiceClass))
				.getSingleResult();
		return Optional.ofNullable(resource);
	}

	protected CriteriaQuery<Resource> getQuery(@NotNull final Class<? extends RESTService> restService) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Resource> query = builder.createQuery(getEntityClass());
		generateWhereClause(builder, query, restService);
		return query;
	}

	protected void generateWhereClause(@NotNull final CriteriaBuilder builder, @NotNull final CriteriaQuery<Resource> query, @NotNull final Class<? extends RESTService> restService) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<Resource> userType = metamodel.entity(getEntityClass());
		Root<Resource> resource = query.from(getEntityClass());

		SingularAttribute<Resource, Class> attribute = userType.getDeclaredSingularAttribute("restService", Class.class);
		Path<Class> servicePath = resource.get(attribute);

		query.where(builder.equal(servicePath, restService));
	}
}
