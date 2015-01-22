package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
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
public class SpecificResourceDatabaseDAO extends DatabaseDAO<SpecificResource> {

	public SpecificResourceDatabaseDAO(@NotNull final EntityManager entityManager) {
		super(entityManager);
	}

	protected SpecificResourceDatabaseDAO() {
	}

	public Optional<SpecificResource> get(@NotNull final Class<? extends RESTService> restServiceClass, @NotEmpty final String id) {
		List<SpecificResource> resultList = getEntityManager()
				.createQuery(getQuery(restServiceClass, id))
				.getResultList();
		if (resultList.isEmpty()) {
			return Optional.empty();
		} else if (resultList.size() == 1) {
			return Optional.of(resultList.get(0));
		} else {
			throw new IllegalStateException("Found more than one specific resource. [Class=" + restServiceClass + ";ID=" + id + ";]");
		}
	}

	protected CriteriaQuery<SpecificResource> getQuery(@NotNull final Class<? extends RESTService> restService, @NotEmpty final String id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SpecificResource> query = builder.createQuery(getEntityClass());
		generateWhereClause(builder, query, restService, id);
		return query;
	}

	protected void generateWhereClause(@NotNull final CriteriaBuilder builder, @NotNull final CriteriaQuery<SpecificResource> query,
	                                   @NotNull final Class<? extends RESTService> restService, @NotEmpty final String id) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<SpecificResource> userType = metamodel.entity(SpecificResource.class);
		Root<SpecificResource> resource = query.from(SpecificResource.class);

		SingularAttribute<? super SpecificResource, Class> serviceAttribute = userType.getSingularAttribute("restService", Class.class);
		Path<Class> servicePath = resource.get(serviceAttribute);
		Predicate servicePredicate = builder.equal(servicePath, restService);

		SingularAttribute<SpecificResource, String> idAttribute = userType.getDeclaredSingularAttribute("resourceID", String.class);
		Path<String> idPath = resource.get(idAttribute);
		Predicate idPredicate = builder.equal(idPath, id);

		query.select(resource);
		query.where(servicePredicate, idPredicate);
	}

	@Override
	protected Class<SpecificResource> getEntityClass() {
		return SpecificResource.class;
	}

	@Override
	protected List<Order> getOrderByParameters(@NotNull final CriteriaBuilder builder, @NotNull final Root<SpecificResource> root) {
		return Arrays.asList(
				builder.asc(root.get("restService")),
				builder.asc(root.get("resourceID"))
		);
	}
}
